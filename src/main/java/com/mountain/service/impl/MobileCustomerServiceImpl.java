package com.mountain.service.impl;

import com.mountain.DAO.MobileCustomerRepository;
import com.mountain.entity.CustomerLocation;
import com.mountain.entity.MobileCustomer;
import com.mountain.service.MobileCustomerService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

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
    public Map<String,Object> saveCustomerLocation(CustomerLocation customerLocation) {
        Map<String,Object> megMap = new HashMap<>();
        String userId = customerLocation.getUserId();
        Query query = new Query(Criteria.where("mobileId").is(userId));
        MobileCustomer verifyOne = mongoTemplate.findOne(query, MobileCustomer.class);
        if (verifyOne == null){
            megMap.put("meg", "the user does not exist, checkin");
        }else {
            megMap.put("msg","successfully upsert new location");
            Update update = new Update();
//            customerLocation.setLogicStatus(1);
            update.push("customerLocation",customerLocation);
//            此处需要加入一个findAndModify设置，当状态为真时，将用户的状态也设置为真
            mongoTemplate.upsert(query,update,MobileCustomer.class);
        }
        return megMap;
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
//            管他是不是0，全设置为0
            customerLocation.setLogicStatus(customerLocation.getLogicStatus() != 0 ? 0 : 0);
            MobileCustomer verifyOne = mongoTemplate.findOne(query, MobileCustomer.class);
            if (verifyOne == null) {
                megMap.put("meg", "the user does not exist, checkin");
            } else {
                Query queryUsers = new Query(Criteria.where("mobileId").is(customerLocation.getUserId()));
                Update update = new Update();
                update.push("customLocation",customerLocation);
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
        System.out.println("--------------------------");
        System.out.println(mongoTemplate.find(query,MobileCustomer.class));
        List<MobileCustomer> mobileCustomers = mongoTemplate.find(query, MobileCustomer.class);
        for (MobileCustomer next : mobileCustomers) {
            System.out.println(next.getCustomerLocation());
        }
        System.out.println("----------------");
        System.out.println(mobileCustomers);
//        List<MobileCustomer> byMobileId = mobileCustomerRepository.findByMobileId();
//        System.out.println("--------------------------------------------");
//        System.out.println(byMobileId);
        TypedAggregation<MobileCustomer> aggregation = newAggregation(MobileCustomer.class,
                project("mobileId"));
        AggregationResults<MobileCustomer> aggregate = mongoTemplate.aggregate(aggregation, MobileCustomer.class);
        AggregationResults<MobileCustomer> mobileId = mongoTemplate.aggregate(aggregation, "mobileId", MobileCustomer.class);
        System.out.println("--------------------------------------mobileid");
        List<MobileCustomer> mappedResults1 = mobileId.getMappedResults();
        System.out.println(mappedResults1);
        System.out.println("--------------------------------------");
        Document rawResults = aggregate.getRawResults();
        List<MobileCustomer> mappedResults = aggregate.getMappedResults();
        for (MobileCustomer mappedResult : mappedResults) {
            System.out.println(mappedResult);
        }
//        System.out.println(rawResults);
        return mobileCustomers;
    }
// 查询用户的状态和全部信息
    @Override
    public List<MobileCustomer> findUserStatus(Boolean status) {
        Query query = new Query(Criteria.where("serviceStatus").is(status));
        return mongoTemplate.find(query, MobileCustomer.class);
    }

//    查询出用户的状态信息
    @Override
    public List<Map<Object,Boolean>> findEmbedDocument() {
//        存入位置
        List<CustomerLocation> customerLocation = null;
//        查询出还未进行服务的用户
        Query query = new Query(Criteria.where("serviceStatus").is(Boolean.FALSE));
        List<MobileCustomer> mobileCustomers = mongoTemplate.find(query, MobileCustomer.class);
        List<Map<Object,Boolean>> mobileList = new LinkedList<>();
        ArrayList<String> userList = new ArrayList<>();
//        存储位置
        List<GeoJsonPoint> jsonPointsList = new ArrayList<>();
        for (MobileCustomer mobileCustomer:mobileCustomers){
            Map<Object,Boolean> statusMap = new HashMap<>();
            statusMap.put(mobileCustomer.getMobileId(),mobileCustomer.getServiceStatus());
            userList.add(mobileCustomer.getMobileId());
            mobileList.add(statusMap);
        }
        for (String list:userList){
            System.out.println(list);
            Query queryCheck = new Query(Criteria.where("mobileId").is(list).and("customerLocation.logicStatus").is(1));
            MobileCustomer checkOne = mongoTemplate.findOne(queryCheck, MobileCustomer.class);
            if (checkOne != null){
                System.out.println("----------");
                System.out.println(checkOne);
            }
//            for (CustomerLocation binLocation:customerLocation){
//                if (binLocation.getLogicStatus() == 1){
//                    jsonPointsList.add(binLocation.getGeoPoint());
//                    System.out.println(binLocation.getGeoPoint());
//                }
//            }

//            防置指针问题
//            System.out.println(checkOne);
//            if (checkOne != null){
//                checkOne.ge
//            }
//            System.out.println("+++++++++++++++++++++++++++");
//            CustomerLocation checkOneLogic = (CustomerLocation) checkOne.getCustomerLocation();
//            int logicStatus = checkOneLogic.getLogicStatus();
//            if (logicStatus == 1)
//            System.out.println(checkOne.getCustomerLocation());
        }
//        如果存在为1的点，以这个点为基准，参与后续的计算表达式
//        Query queryOne = new Query(Criteria.where("customerLocation.logicStatus").is(1));
////    根据id查询用户的位置信息
//        Query queryEmbed = new Query(Criteria.where("customerLocation.userId").is("520"));
////        增强for迭代出来
//        MobileCustomer one = mongoTemplate.findOne(queryEmbed, MobileCustomer.class);
//        System.out.println(one);

        Aggregation aggregation = newAggregation(unwind("customerLocation"),group("customerLocation."));
        return mobileList;
    }

}
