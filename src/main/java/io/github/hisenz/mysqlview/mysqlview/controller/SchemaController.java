package io.github.hisenz.mysqlview.mysqlview.controller;

import io.github.hisenz.mysqlview.mysqlview.entity.Schemata;
import io.github.hisenz.mysqlview.mysqlview.msg.ResponseMsg;
import io.github.hisenz.mysqlview.mysqlview.service.SchemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/schema")
@CrossOrigin
public class SchemaController {
    @Autowired
    private SchemaService schemaService;
    @GetMapping("/list")
    public ResponseMsg listSchema(String dataSourceName) {
        List<Schemata> resultList = schemaService.findAll();
        return ResponseMsg.create("", true, resultList);
    }
}
