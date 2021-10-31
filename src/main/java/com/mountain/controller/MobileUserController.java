package com.mountain.controller;

import com.mountain.entity.CustomerLocation;
import com.mountain.entity.MobileCustomer;
import com.mountain.service.impl.MobileCustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

/**
 * @description:
 * @author: mountainINblack
 * @date: 2021/10/30 19:09
 */
@RestController
@RequestMapping("/mobile")
public class MobileUserController {
    @Autowired
    private MobileCustomerServiceImpl mobileCustomerService;
    @PostMapping("/saveusers")
    public Map<String,Object> insertMobileUser(MobileCustomer mobileCustomer){
        mobileCustomer.setCreate_time(new Date());
        return mobileCustomerService.insertMobileUsers(mobileCustomer);
    }
    @PostMapping("/usermove")
    public String upsertUserLocation(CustomerLocation customerLocation){
        customerLocation.setService_time(new Date());
        return mobileCustomerService.saveCustomerLocation(customerLocation);
    }
    @GetMapping("/findbyid")
    public MobileCustomer findByUserId(String id){
        System.out.println(id);
        MobileCustomer one = mobileCustomerService.findByUersId(id);
        System.out.println(one);
        return one;
    }
}
