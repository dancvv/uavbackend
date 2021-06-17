package com.mountain.service;

import com.mountain.DAO.OrderDAO;
import com.mountain.entity.Order;

import java.util.Date;
import java.util.List;

public class searchServiceImpl implements searchService{
    OrderDAO orderDAO;
    @Override
    public void insertCustom(Order order) {
        orderDAO.insertOrder(order);
    }

    @Override
    public Order selectByDate(Date date) {
        return orderDAO.selectCustomByDate(date);
    }

    @Override
    public Order selectById(Integer id) {
        return orderDAO.selectCustomById(id);
    }

    @Override
    public List<Order> selectOrderList() {
        return orderDAO.selectOrderList();
    }
}
