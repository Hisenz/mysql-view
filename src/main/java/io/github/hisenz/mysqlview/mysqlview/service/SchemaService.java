package io.github.hisenz.mysqlview.mysqlview.service;

import io.github.hisenz.mysqlview.mysqlview.entity.Schemata;

import java.util.List;

public interface SchemaService {
    List<Schemata> findAll();
}
