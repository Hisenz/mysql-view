package io.github.hisenz.mysqlview.mysqlview.service.impl;

import io.github.hisenz.mysqlview.mysqlview.entity.View;
import io.github.hisenz.mysqlview.mysqlview.mapper.ViewsDao;
import io.github.hisenz.mysqlview.mysqlview.service.ViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ViewServiceImpl implements ViewService {
    @Autowired
    ViewsDao viewsDao;
    @Override
    public List<View> findBySchema(String schema) {
        return viewsDao.findAllBySchema(schema);
    }
}
