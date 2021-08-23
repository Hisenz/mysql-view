package io.github.hisenz.mysqlview.mysqlview.mapper;

import io.github.hisenz.mysqlview.mysqlview.entity.View;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ViewsDao {
    int insert(View record);

    int insertSelective(View record);

    List<View> findAllBySchema(String schema);
}