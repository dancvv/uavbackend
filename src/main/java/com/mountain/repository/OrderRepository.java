package com.mountain.repository;

import com.mountain.entity.Order;
//查找乘客的接口
public interface OrderRepository {
    Iterable<Order> findAll();
    Order findOne(int id);
//    没有加保存的方法

}
