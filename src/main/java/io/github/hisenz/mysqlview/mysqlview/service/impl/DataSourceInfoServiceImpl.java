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
            return dataSourceInfoMapper.update(info);
        }
        return dataSourceInfoMapper.add(info);
    }


    @Override
    public DataSourceInfo findByName(String dataSource) {
        return dataSourceInfoMapper.findByName(dataSource);
    }

    @Override
    public boolean remove(int id) {
        DataSourceInfo dataSourceInfo = dataSourceInfoMapper.findById(id);
        return dataSourceInfoMapper.deleteById(dataSourceInfo);
    }
}
