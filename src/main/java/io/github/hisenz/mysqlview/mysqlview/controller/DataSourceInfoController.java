package io.github.hisenz.mysqlview.mysqlview.controller;

import io.github.hisenz.mysqlview.mysqlview.entity.DataSourceInfo;
import io.github.hisenz.mysqlview.mysqlview.msg.ResponseMsg;
import io.github.hisenz.mysqlview.mysqlview.service.DataSourceInfoService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
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


    @PostMapping
    public ResponseMsg appendOrUpdate(@RequestBody DataSourceInfo info) {
        return ResponseMsg.create("", dataSourceInfoService.appendOrUpdate(info));
    }


    @DeleteMapping("/{id}")
    public ResponseMsg delete(@PathVariable int id) {
        return ResponseMsg.create("", dataSourceInfoService.remove(id));
    }


    @PostMapping("/testConnection")
    public ResponseMsg testConnect(@RequestBody DataSourceInfo info) {
        boolean validation = dataSourceInfoService.validation(info);
        return ResponseMsg.create("", validation);
    }
}
