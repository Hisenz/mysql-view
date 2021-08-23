package io.github.hisenz.mysqlview.mysqlview.service;

import io.github.hisenz.mysqlview.mysqlview.entity.Table;

import java.util.List;

public interface tableService {
    List<Table> findBySchema(String schema);
}
