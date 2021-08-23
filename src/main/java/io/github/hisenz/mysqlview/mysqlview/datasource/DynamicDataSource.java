package io.github.hisenz.mysqlview.mysqlview.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;

public class DynamicDataSource extends AbstractRoutingDataSource {
    private final Map<Object, Object> backupTargetDataSource;

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

    /**
     * 添加数据源
     *
     * @param key key of DataSource
     * @param dataSource DataSource
     */
    public void addDataSource(String key, DataSource dataSource) {
        backupTargetDataSource.put(key, dataSource);
        super.setTargetDataSources(backupTargetDataSource);
        super.afterPropertiesSet();
    }
}
