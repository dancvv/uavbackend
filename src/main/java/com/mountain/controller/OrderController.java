package com.mountain.controller;

import com.mountain.entity.Order;
import com.mountain.service.searchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class OrderController {
    @Autowired
    searchService service;
    @PostMapping("/addcustomer")
    public void insertCustom(@RequestBody Order order){
        service.insertCustom(order);
    }
    @GetMapping("/customer/{date}")
    public Order selectByDate(@PathVariable Date date){
        System.err.println("the value of "+date);
        return service.selectByDate(date);
    }
    @GetMapping("/CustomID/{id}")
    public Order selectCustomById(@PathVariable("id") Integer id){
        return service.selectById(id);

    }
    @GetMapping("/Customlist")
    public List<Order> selectCustomerList(){
        return service.selectOrderList();
    }
}
