package io.github.hisenz.mysqlview.mysqlview.mapper;

import io.github.hisenz.mysqlview.mysqlview.entity.DataSourceInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DataSourceInfoMapper {
    List<DataSourceInfo> findAll();

    int add(DataSourceInfo info);

    int update(DataSourceInfo info);

    int deleteById(int id);

    DataSourceInfo findByName(String name);
}