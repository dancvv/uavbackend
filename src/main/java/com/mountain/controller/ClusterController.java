package com.mountain.controller;

import java.util.List;

import com.mountain.entity.ClusterResult;
import com.mountain.service.impl.ClusterServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/cluster")
public class ClusterController {

    @Autowired
    private ClusterServiceImpl clusterServicel;
    
    @GetMapping("findall")
    public List<ClusterResult> findAll(){
        List<ClusterResult> res = clusterServicel.findAll();
        return res;
    }
}
