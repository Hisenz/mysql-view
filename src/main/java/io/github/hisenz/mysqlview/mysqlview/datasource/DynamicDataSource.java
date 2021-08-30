package io.github.hisenz.mysqlview.mysqlview.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import io.github.hisenz.mysqlview.mysqlview.entity.DataSourceInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

public class DynamicDataSource extends AbstractRoutingDataSource {
    private final Map<Object, Object> backupTargetDataSource;

    private static final Logger LOGGER = LoggerFactory.getLogger(DynamicDataSource.class);

    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContext.getContextKey();
    }

    public DynamicDataSource(DataSource defaultDataSource, Map<Object, Object> targetDataSource) {
        backupTargetDataSource = targetDataSource;
        super.setDefaultTargetDataSource(defaultDataSource);
        super.setTargetDataSources(targetDataSource);
        super.afterPropertiesSet();
    }

    public void remove(String key) {
        backupTargetDataSource.remove(key);
        LOGGER.info("datasource: {} has be delete", key);
        super.setTargetDataSources(backupTargetDataSource);
        super.afterPropertiesSet();
    }

    public void addDataSources(List<DataSourceInfo> dataSourceInfos) {
        dataSourceInfos.forEach(this::addDataSource);
    }

    public void addDataSource(DataSourceInfo info) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(info.getDriver());
        dataSource.setPassword(info.getPassword());
        dataSource.setUsername(info.getUsername());
        dataSource.setUrl(info.getUrl());
        addDataSource(info.getName(), dataSource);
    }

    /**
     * 添加数据源
     *
     * @param key        key of DataSource
     * @param dataSource DataSource
     */
    public void addDataSource(String key, DataSource dataSource) {
        LOGGER.info("datasource: {} appended, info: {}", key, dataSource.getClass());
        backupTargetDataSource.put(key, dataSource);
        super.setTargetDataSources(backupTargetDataSource);
        super.afterPropertiesSet();
    }
}
