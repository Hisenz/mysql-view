package io.github.hisenz.mysqlview.mysqlview.service;

import io.github.hisenz.mysqlview.mysqlview.entity.DataSourceInfo;

import java.sql.SQLException;
import java.util.List;

public interface DataSourceInfoService {
    void exchange(DataSourceInfo dataSourceInfo) throws NoSuchFieldException, IllegalAccessException, SQLException;

    List<DataSourceInfo> findAll();

    boolean append(DataSourceInfo info);

    boolean change(DataSourceInfo info);

    boolean remove(int id);
}
