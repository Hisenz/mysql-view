package io.github.hisenz.mysqlview.mysqlview.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SchemataDaoTest {
    @Autowired
    SchemataDao schemataDao;
    @Test
    public void insert() {
    }

    @Test
    public void insertSelective() {
    }
    @Test
    public void findAll() {
        System.out.println(schemataDao.findAll());
    }
}