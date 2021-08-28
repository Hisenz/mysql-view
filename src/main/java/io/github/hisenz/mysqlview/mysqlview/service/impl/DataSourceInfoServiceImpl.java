package io.github.hisenz.mysqlview.mysqlview.service.impl;

import com.alibaba.druid.pool.DruidAbstractDataSource;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSONObject;
import io.github.hisenz.mysqlview.mysqlview.costant.RedisConstants;
import io.github.hisenz.mysqlview.mysqlview.entity.DataSourceInfo;
import io.github.hisenz.mysqlview.mysqlview.mapper.DataSourceInfoMapper;
import io.github.hisenz.mysqlview.mysqlview.service.DataSourceInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Service
public class DataSourceInfoServiceImpl extends ApplicationEvent implements DataSourceInfoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceInfoServiceImpl.class);
    private final DataSourceInfoMapper dataSourceInfoMapper;
    private final DataSource dataSource;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public DataSourceInfoServiceImpl(DataSourceInfoMapper dataSourceInfoMapper, DataSource dataSource) {
        super(DataSourceInfoServiceImpl.class);
        this.dataSourceInfoMapper = dataSourceInfoMapper;
        this.dataSource = dataSource;

    }

    @Override
    public void exchange(DataSourceInfo dataSourceInfo) throws NoSuchFieldException, IllegalAccessException, SQLException {
        DataSourceInfo o = (DataSourceInfo) redisTemplate.opsForHash().get(RedisConstants.DATA_SOURCE_KEY, RedisConstants.DATA_SOURCE_CURRENT_KEY);
        if (Objects.equals(o, dataSourceInfo)) {
            LOGGER.info("data source info not change, do not exchange");
            return;
        }
        DruidDataSource druidDataSource = (DruidDataSource) this.dataSource;
        Field username = DruidAbstractDataSource.class.getDeclaredField("username");
        Field jdbcUrl = DruidAbstractDataSource.class.getDeclaredField("jdbcUrl");
        Field driverClass = DruidAbstractDataSource.class.getDeclaredField("driverClass");
        username.setAccessible(true);
        jdbcUrl.setAccessible(true);
        driverClass.setAccessible(true);
        username.set(dataSource, dataSourceInfo.getUsername());
        jdbcUrl.set(dataSource, dataSourceInfo.getUrl());
        driverClass.set(dataSource, dataSourceInfo.getDriver());
        druidDataSource.setPassword(dataSourceInfo.getPassword());
        druidDataSource.restart();
    }

    @Override
    public boolean validation(DataSourceInfo dataSourceInfo) {
        try {
            // z注册驱动
            Class.forName(dataSourceInfo.getDriver());
            // 打开链接
            Connection connection = DriverManager.getConnection(dataSourceInfo.getUrl(), dataSourceInfo.getUsername(), dataSourceInfo.getPassword());
            connection.close();
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.error("connect database failed, connectInfo: {}, reason: {}", JSONObject.toJSONString(dataSourceInfo), e.getMessage());
        }
        return false;
    }

    @Override
    public List<DataSourceInfo> findAll() {
        return dataSourceInfoMapper.findAll();
    }

    @Override
    public boolean appendOrUpdate(DataSourceInfo info) {
        if (!validation(info)) {
            return false;
        }
        if (dataSourceInfoMapper.findByName(info.getName()) != null) {
            return dataSourceInfoMapper.update(info) == 1;
        }
        return dataSourceInfoMapper.add(info) == 1;
    }

    @Override
    public DataSourceInfo findByName(String dataSource) {
        return dataSourceInfoMapper.findByName(dataSource);
    }

    @Override
    public boolean remove(int id) {
        return dataSourceInfoMapper.deleteById(id) == 1;
    }
}
