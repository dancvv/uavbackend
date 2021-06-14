package com.mountain.repository;

import com.mountain.entity.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class jdbcOrderRepository implements OrderRepository{
    private JdbcTemplate jdbc;

    public jdbcOrderRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Iterable<Order> findAll() {
        return jdbc.query("select * from orderdata limit 0,20",this::mapRowToOrder);
    }

    @Override
    public Order findOne(int id) {
        return null;
    }
    private Order mapRowToOrder(ResultSet rs,int rowNum) throws SQLException {
//                此处需要返回所有的order数据不然报错
        return new Order(
                rs.getInt("id"),
                rs.getString("passenger_id"),
                rs.getDate("date"),
                rs.getDate("create_time"),
                rs.getDate("check_ticket_time"),
                rs.getInt("up_stop_id"),
                rs.getInt("down_stop_id"),
                rs.getInt("ride_count"),
                rs.getString("small_vehicle_id"),
                rs.getString("car_id")
        );
    }
}