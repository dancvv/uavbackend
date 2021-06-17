package com.mountain.service;

import com.mountain.entity.Order;

import java.util.Date;
import java.util.List;

public interface searchService {
    void insertCustom(Order order);
    Order selectByDate(Date date);
    Order selectById(Integer id);
    List<Order> selectOrderList();

}
