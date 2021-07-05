package com.mountain.service;

import com.mountain.DAO.OrderDAO;
import com.mountain.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class searchServiceImpl implements searchService{
    @Autowired
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
