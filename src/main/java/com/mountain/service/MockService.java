package com.mountain.service;

import com.mountain.DAO.MockDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MockService {
    @Autowired
    private MockDAO mockDAO;
    public List<Map<String,Object>> queryList(){
        List<Map<String, Object>> DaoList = mockDAO.getList();
//        DaoList.contains("up_stop_id");
//        System.out.println(DaoList.contains("up_stop_id"));
        return mockDAO.getList();
    }




}
