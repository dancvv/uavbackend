package com.mountain.service.impl;

import com.mountain.DAO.MobileCustomerRepository;
import com.mountain.entity.CustomerLocation;
import com.mountain.entity.MobileCustomer;
import com.mountain.service.MobileCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: mountainINblack
 * @date: 2021/10/30 17:21
 */
@Service
public class MobileCustomerServiceImpl implements MobileCustomerService {
    @Autowired
    private MobileCustomerRepository mobileCustomerRepository;
    @Override
    public Map<String,Object> insertMobileUsers(MobileCustomer mobileCustomer) {
        Map<String,Object> megMap = new HashMap<>();
        try{
            mobileCustomerRepository.insert(mobileCustomer);
            megMap.put("msg","successfully add");
        }catch (Exception e){
            System.out.println(e);
            megMap.put("msg","something wrong happened");
        }
        return megMap;
    }

    @Override
    public String saveCustomerLocation(CustomerLocation customerLocation) {
        return null;
    }

    @Override
    public String deleteByUserId(String id) {
        return null;
    }

    @Override
    public String deleteAllUsers() {
        return null;
    }

    @Override
    public String removeLocationByUserId(String id) {
        return null;
    }
}
