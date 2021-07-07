package com.mountain.controller;

import com.mountain.entity.Order;
import com.mountain.service.searchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@RestController
public class OrderController {
    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        dateFormat.setLenient(false);
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
    @Autowired
    searchService service;
    @PostMapping("/addcustomer")
    public void insertCustom(@RequestBody Order order){
        service.insertCustom(order);
    }
    @GetMapping("/customer")
    public Order selectByDate(@RequestParam("date") Date date){

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
