package io.github.hisenz.mysqlview.mysqlview.service.impl;

import io.github.hisenz.mysqlview.mysqlview.entity.DataSourceInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;
@SpringBootTest
class DataSourceInfoServiceImplTest {

    @Autowired
    DataSourceInfoServiceImpl dataSourceInfoService;
    @Test
    void refresh() throws SQLException, NoSuchFieldException, IllegalAccessException {
        DataSourceInfo dataSourceInfo = new DataSourceInfo();
        dataSourceInfo.setDriver("com.alibaba.druid.pool.DruidDataSource");
        dataSourceInfo.setUsername("paas");
        dataSourceInfo.setUrl("jdbc:mysql://localhost:3306/mysql_view?allowMultiQueries=true&useUnicode&characterEncoding=UTF-8&autoReconnect=true&useSSL=true&verifyServerCertificate=false&requireSSL=true");
        dataSourceInfo.setPassword("hello");
        dataSourceInfoService.exchange(dataSourceInfo);
    }

    @Test
    void findAll() {
    }

    @Test
    void append() {
    }

    @Test
    void change() {
    }

    @Test
    void remove() {
    }
}