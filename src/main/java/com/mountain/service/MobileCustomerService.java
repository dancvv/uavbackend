package com.mountain.service;

import com.mountain.entity.CustomerLocation;
import com.mountain.entity.MobileCustomer;

import java.util.Map;

/**
 * @description: service
 * @author: mountainINblack
 * @date: 2021/10/30 17:20
 */
public interface MobileCustomerService {
    Map<String,Object> insertMobileUsers(MobileCustomer mobileCustomer);
    String saveCustomerLocation(CustomerLocation customerLocation);
    String deleteByUserId(String id);
    String deleteAllUsers();
    String removeLocationByUserId(String id);
}
