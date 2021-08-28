package io.github.hisenz.mysqlview.mysqlview.mapper;

import io.github.hisenz.mysqlview.mysqlview.entity.Table;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface TablesDao {
    int insert(Table record);

    int insertSelective(Table record);

    List<Table> findBySchema(String schema);

    List<Map<String, Object>> findData(String schema, String tableName);

    boolean isExists(String schema, String tableName);
}