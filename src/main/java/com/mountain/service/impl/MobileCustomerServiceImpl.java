package com.mountain.service.impl;

import com.mountain.DAO.MobileCustomerRepository;
import com.mountain.entity.CustomerLocation;
import com.mountain.entity.MobileCustomer;
import com.mountain.service.MobileCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
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
    @Autowired
    private MongoTemplate mongoTemplate;
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
        String userId = customerLocation.getUserId();
        Query query = new Query(Criteria.where("mobileId").is(userId));
        Update update = new Update();
        update.push("customerLocation",customerLocation);
        mongoTemplate.upsert(query,update,MobileCustomer.class);
        return "successfully upsert new location";
    }

    @Override
    public String deleteByUserId(String id) {
        mobileCustomerRepository.deleteMobileCustomerByMobileId(id);
        return "successfully delete";
    }

    @Override
    public String deleteAllUsers() {
        return null;
    }

    @Override
    public String removeLocationByUserId(String id) {
        return null;
    }

    public MobileCustomer findByUersId(String id){
        Query query = new Query(Criteria.where("mobileId").is(id));
        MobileCustomer byId = mongoTemplate.findOne(query, MobileCustomer.class);
        System.out.println(byId);
        return byId;
    }

    @Override
    public Map<String,Object> insertManyMobileUsers(List<MobileCustomer> customerList) {
        Map<String,Object> megMap = new HashMap<>();
        try {
            Collection<MobileCustomer> insert = mongoTemplate.insert(customerList, MobileCustomer.class);
            megMap.put("meg","successfully add");
        }catch (Exception e){
            System.out.println(e);
            megMap.put("meg","failure");
        }
        return megMap;

    }
}
