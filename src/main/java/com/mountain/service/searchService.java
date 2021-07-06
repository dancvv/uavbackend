package com.mountain.service;

import com.mountain.entity.Order;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface searchService {
    void insertCustom(Order order);
    Order selectByDate(String date);

//    Order selectByDate(LocalDate date);

    Order selectById(Integer id);
    List<Order> selectOrderList();

}
