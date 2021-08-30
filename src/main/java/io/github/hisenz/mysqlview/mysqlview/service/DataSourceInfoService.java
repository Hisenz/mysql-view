package io.github.hisenz.mysqlview.mysqlview.service;

import io.github.hisenz.mysqlview.mysqlview.entity.DataSourceInfo;

import java.util.List;

public interface DataSourceInfoService {

    List<DataSourceInfo> findAll();

    boolean remove(int id);

    boolean validation(DataSourceInfo info);

    boolean appendOrUpdate(DataSourceInfo info);

    DataSourceInfo findByName(String dataSource);
}
