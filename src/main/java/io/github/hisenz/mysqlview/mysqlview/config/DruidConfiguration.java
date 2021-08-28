package io.github.hisenz.mysqlview.mysqlview.config;

import com.alibaba.druid.pool.DruidDataSource;
import io.github.hisenz.mysqlview.mysqlview.datasource.DynamicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;

@Configuration
public class DruidConfiguration {
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.driver-class-name}")
    private String driver;

    /**
     * 声明其为Bean实例 @Primary // 在同样的DataSource中，首先使用被标注的DataSource
     */
    @Bean
    public DataSource dataSource() throws SQLException {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(url);
        druidDataSource.setDriverClassName(driver);
        druidDataSource.setUsername(username);
        druidDataSource.setPassword(password);
        return new DynamicDataSource(druidDataSource, new HashMap<>());
    }
}
