package com.mountain.service.impl;

import java.util.List;

import com.mountain.Mapper.ClusterMapper;
import com.mountain.entity.ClusterResult;
import com.mountain.service.ClusterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClusterServiceImpl implements ClusterService{

    @Autowired
    private ClusterMapper clusterMapper;
    @Override
    public List<ClusterResult> findAll() {
        // TODO Auto-generated method stub
        List<ClusterResult> res = clusterMapper.findAll();
        return res;
    }
    
}
