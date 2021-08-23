package io.github.hisenz.mysqlview.mysqlview.service.impl;

import io.github.hisenz.mysqlview.mysqlview.entity.Table;
import io.github.hisenz.mysqlview.mysqlview.mapper.TablesDao;
import io.github.hisenz.mysqlview.mysqlview.service.tableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class tableServiceImpl implements tableService {
    @Autowired
    TablesDao tablesDao;
    @Override
    public List<Table> findBySchema(String schema) {
        return tablesDao.findBySchema(schema);
    }
}
