package io.github.hisenz.mysqlview.mysqlview.service.impl;

import io.github.hisenz.mysqlview.mysqlview.entity.Schemata;
import io.github.hisenz.mysqlview.mysqlview.mapper.SchemataDao;
import io.github.hisenz.mysqlview.mysqlview.service.SchemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchemaServiceImpl implements SchemaService {
    @Autowired
    private SchemataDao schemataDao;

    @Override
    public List<Schemata> findAll() {
        return schemataDao.findAll();
    }
}
