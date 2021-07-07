package com.mountain.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class MockDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public List<Map<String,Object>> getList(){
        List<Map<String,Object>> list=jdbcTemplate.queryForList("select * from orderdata ");
        return list;
    }
}
