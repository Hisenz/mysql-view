package io.github.hisenz.mysqlview.mysqlview.listener;

import io.github.hisenz.mysqlview.mysqlview.costant.RedisConstants;
import io.github.hisenz.mysqlview.mysqlview.entity.DataSourceInfo;
import io.github.hisenz.mysqlview.mysqlview.service.DataSourceInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class DataSourceListener implements CommandLineRunner {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private DataSourceInfoService dataSourceInfoService;
    @Autowired
    private DataSource dataSource;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.driver-class-name}")
    private String driver;

    @Override
    public void run(String... args) throws Exception {
        initDataSourceConfig();
    }

    /**
     * 启动时将当前连接的数据库信息存入Redis缓存
     */
    private void initDataSourceConfig() {
        final DataSourceInfo dataSourceInfo = new DataSourceInfo();
        dataSourceInfo.setUrl(url);
        dataSourceInfo.setPassword(password);
        dataSourceInfo.setUsername(username);
        dataSourceInfo.setDriver(driver);
        final HashOperations<String, Object, Object> opsForHash = redisTemplate.opsForHash();
        opsForHash.put(RedisConstants.DATA_SOURCE_KEY, RedisConstants.DATA_SOURCE_DEFAULT_KEY, dataSourceInfo);
        opsForHash.put(RedisConstants.DATA_SOURCE_KEY, RedisConstants.DATA_SOURCE_CURRENT_KEY, dataSourceInfo);
    }
}
