package com.mountain.controller;

import com.mountain.entity.CustomerLocation;
import com.mountain.entity.MobileCustomer;
import com.mountain.service.impl.MobileCustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        mobileCustomer.setCreateTime(new Date());
        return mobileCustomerService.insertMobileUsers(mobileCustomer);
    }
    @PostMapping("/usermove")
    public String upsertUserLocation(CustomerLocation customerLocation){
        customerLocation.setServiceTime(new Date());
        return mobileCustomerService.saveCustomerLocation(customerLocation);
    }
    @GetMapping("/findbyid")
    public MobileCustomer findByUserId(String id){
        return mobileCustomerService.findByUersId(id);
    }
    @DeleteMapping("/deleteuser")
    public String deleteUserById(String id){
        return mobileCustomerService.deleteByUserId(id);
    }
}
