package io.github.hisenz.mysqlview.mysqlview.controller;

import io.github.hisenz.mysqlview.mysqlview.entity.View;
import io.github.hisenz.mysqlview.mysqlview.msg.ResponseMsg;
import io.github.hisenz.mysqlview.mysqlview.service.ViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/view")
public class ViewController {
    @Autowired
    private ViewService viewService;

    @GetMapping("/{schema}")
    public ResponseMsg list(@PathVariable String schema) {
        List<View> viewList = viewService.findBySchema(schema);
        return ResponseMsg.create("", true, viewList);
    }
}
