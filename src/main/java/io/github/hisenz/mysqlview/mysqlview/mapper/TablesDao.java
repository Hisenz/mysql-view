package io.github.hisenz.mysqlview.mysqlview.mapper;

import io.github.hisenz.mysqlview.mysqlview.entity.Table;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TablesDao {
    int insert(Table record);

    int insertSelective(Table record);

    List<Table> findBySchema(String schema);
}