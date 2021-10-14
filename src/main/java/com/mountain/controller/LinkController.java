package com.mountain.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@CrossOrigin
@RestController
@RequestMapping("/")
public class LinkController {
    @GetMapping("/test")
    public Map<String,Object> linkTest(){
        Map<String,Object> infoMap =new HashMap<>();
        infoMap.put("status",200);
        infoMap.put("msg","连接正常");
        infoMap.put("info","success");
        return infoMap;
    }
}
