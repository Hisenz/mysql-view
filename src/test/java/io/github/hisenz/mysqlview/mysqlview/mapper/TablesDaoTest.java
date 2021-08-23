package io.github.hisenz.mysqlview.mysqlview.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TablesDaoTest {
    @Autowired
    TablesDao tablesDao;
    @Test
    void insert() {
    }

    @Test
    void insertSelective() {
    }

    @Test
    void findBySchema() {
        System.out.println(tablesDao.findBySchema(null));
    }
}