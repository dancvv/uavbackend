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

import java.util.*;

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

    @Override
    public Map<String, Object> updateManyUsersLocation(List<CustomerLocation> customerLocationList) {
        Map<String,Object> megMap = new HashMap<>();
//        CustomerLocation tempLocation = new CustomerLocation();
        for (CustomerLocation customerLocation : customerLocationList) {
            Query query = new Query(Criteria.where("mobileId").is(customerLocation.getUserId()));
            MobileCustomer verifyOne = mongoTemplate.findOne(query, MobileCustomer.class);
            if (verifyOne == null) {
                megMap.put("meg", "the user does not exist, checkin");
            } else {
                Query queryUsers = new Query(Criteria.where("mobileId").is(customerLocation.getUserId()));
                Update update = new Update();
                mongoTemplate.upsert(queryUsers, update, MobileCustomer.class);
                megMap.put("meg","successfully insert");
            }
        }
        return megMap;
    }

    @Override
    public List<MobileCustomer> LocationCompare() {
        Map<String,Object> megMap = new HashMap<>();
//        先根据id查询单个用户的最新位置
        Query query = new Query();
//        必须包含location，不包含则会出现location为null值
//        查询某一个userid，得到其坐标
//        查询到最新的坐标，根据最新坐标来计算覆盖范围是否在50m内
        query.fields().include("customerLocation");
        List<MobileCustomer> mobileCustomers = mongoTemplate.find(query, MobileCustomer.class);
        ListIterator<MobileCustomer> mobileCustomerListIterator = mobileCustomers.listIterator();
        while (mobileCustomerListIterator.hasNext()){
            MobileCustomer next = mobileCustomerListIterator.next();
            System.out.println(next.getCustomerLocation());
        }
        System.out.println(mobileCustomers);
//        List<MobileCustomer> byMobileId = mobileCustomerRepository.findByMobileId();
//        System.out.println("--------------------------------------------");
//        System.out.println(byMobileId);
        return mobileCustomers;
    }
}
