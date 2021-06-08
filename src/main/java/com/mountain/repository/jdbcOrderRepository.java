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
        return jdbc.query("select * from orderdata limit 0,20", new mapRowToOrder());
    }

    @Override
    public Order findOne(int id) {
        return null;
    }
    private Order mapRowToOrder(ResultSet rs,int rowNum) throws SQLException {
        return new Order(
                rs.getString("id");
                rs.getString("passenger_id");
                rs.getString("date");
        );
    }
}
