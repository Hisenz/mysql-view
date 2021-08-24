package io.github.hisenz.mysqlview.mysqlview.controller;

import io.github.hisenz.mysqlview.mysqlview.entity.Table;
import io.github.hisenz.mysqlview.mysqlview.msg.ResponseMsg;
import io.github.hisenz.mysqlview.mysqlview.service.tableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/table")
public class TableController {
    @Autowired
    private tableService tableService;

    @GetMapping("/{schema}")
    public ResponseMsg list(@PathVariable String schema) {
        List<Table> tableList = tableService.findBySchema(schema);
        return ResponseMsg.create("", true, tableList);
    }
}
