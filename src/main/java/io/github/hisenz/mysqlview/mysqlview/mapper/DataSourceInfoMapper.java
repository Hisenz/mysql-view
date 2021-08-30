package io.github.hisenz.mysqlview.mysqlview.mapper;

import io.github.hisenz.mysqlview.mysqlview.entity.DataSourceInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DataSourceInfoMapper {
    List<DataSourceInfo> findAll();

    Boolean add(DataSourceInfo info);

    Boolean update(DataSourceInfo info);

    Boolean deleteById(DataSourceInfo info);

    DataSourceInfo findById(int id);

    DataSourceInfo findByName(String name);
}