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
        List<Map<String,Object>> list=jdbcTemplate.queryForList("select * from orderdata where DATE = '2017-04-01'");
//        System.out.println(list.size());
        return list;
    }
    public List<Map<String,Object>> getPeople(){
        List<Map<String,Object>> list=jdbcTemplate.queryForList("SELECT up_stop_id,count(up_stop_id) as result from orderdata WHERE date = '2017-04-01'  GROUP BY up_stop_id");
        return list;
    }


}
