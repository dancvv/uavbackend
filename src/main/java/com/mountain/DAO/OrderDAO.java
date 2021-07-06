package com.mountain.DAO;

import com.mountain.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
@Repository
public class OrderDAO {
    @Autowired
    JdbcTemplate jdbcTem;
    @Deprecated
    public void insertOrder(Order order){
//        暂时不用修改功能
//        String sql="insert into order values(?,?,?,?,?,?,?)";
//        jdbcTem.update(sql);
    }
    public Order selectCustomByDate(String date){
        Date dat = null;
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        try {
            dat=dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return jdbcTem.queryForObject("select * from orderdata where date =? ",new BeanPropertyRowMapper<Order>(Order.class),dat);
    }
    public Order selectCustomById(Integer id){
        return jdbcTem.queryForObject("select * from orderdata where id=? ",new BeanPropertyRowMapper<Order>(Order.class),id);
    }
    public List<Order> selectOrderList(){
        return jdbcTem.query("select * from orderdata limit 0,20",new BeanPropertyRowMapper<Order>(Order.class));
    }
}
