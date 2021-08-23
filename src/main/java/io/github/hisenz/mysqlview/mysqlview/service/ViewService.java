package io.github.hisenz.mysqlview.mysqlview.service;

import io.github.hisenz.mysqlview.mysqlview.entity.View;

import java.util.List;

public interface ViewService {
    List<View> findBySchema(String schema);
}
