package io.github.hisenz.mysqlview.mysqlview.controller;

import io.github.hisenz.mysqlview.mysqlview.entity.DataSourceInfo;
import io.github.hisenz.mysqlview.mysqlview.msg.ResponseMsg;
import io.github.hisenz.mysqlview.mysqlview.service.DataSourceInfoService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dataSource")
public class DataSourceInfoController {
    private final DataSourceInfoService dataSourceInfoService;

    public DataSourceInfoController(DataSourceInfoService dataSourceInfoService) {
        this.dataSourceInfoService = dataSourceInfoService;
    }

    @GetMapping
    public ResponseMsg getList() {
        return ResponseMsg.create("", true, dataSourceInfoService.findAll());
    }

    @PutMapping
    public ResponseMsg append(@RequestBody DataSourceInfo info) {
        return ResponseMsg.create("", dataSourceInfoService.append(info));
    }

    @PostMapping
    public ResponseMsg update(@RequestBody DataSourceInfo info) {
        return ResponseMsg.create("", dataSourceInfoService.change(info));
    }


    @DeleteMapping("/{id}")
    public ResponseMsg update(@PathVariable int id) {
        return ResponseMsg.create("", dataSourceInfoService.remove(id));
    }

}
