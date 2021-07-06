package com.mountain.controller;

import com.mountain.service.MockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class MockController {
    @Autowired
    MockService mockService;

    @GetMapping("/mock")
    public List<Map<String,Object>> MockApi(){
        List<Map<String,Object>> list_map= mockService.queryList();
        return list_map;
    }
}
