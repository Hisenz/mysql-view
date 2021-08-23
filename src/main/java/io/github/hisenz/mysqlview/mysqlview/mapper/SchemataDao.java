package io.github.hisenz.mysqlview.mysqlview.mapper;

import io.github.hisenz.mysqlview.mysqlview.entity.Schemata;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SchemataDao {
    int insert(Schemata record);

    int insertSelective(Schemata record);

    List<Schemata> findAll();
}