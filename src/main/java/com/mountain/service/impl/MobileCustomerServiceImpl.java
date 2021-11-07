package com.mountain.service.impl;

import com.mongodb.client.result.UpdateResult;
import com.mountain.DAO.MobileCustomerRepository;
import com.mountain.entity.AggrMobileResults;
import com.mountain.entity.CustomerLocation;
import com.mountain.entity.MobileCustomer;
import com.mountain.service.MobileCustomerService;
import org.bson.Document;
import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GlobalCoordinates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.AggregationUpdate;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.springframework.data.domain.Sort.Direction.DESC;
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
    @Autowired
    private CommonUtilsServiceImpl commonUtilsService;
    @Override
    public Map<String,Object> insertMobileUsers(MobileCustomer mobileCustomer) {
        Map<String,Object> megMap = new HashMap<>();
        try{
//            需要加入判断机制，判断添加的用户唯一存在
            Query query = new Query(Criteria.where("mobileId").is(mobileCustomer.getMobileId()));
            MobileCustomer one = mongoTemplate.findOne(query, MobileCustomer.class);
            if (one!=null){
                megMap.put("error","this user has already exists");
            }else {
                mobileCustomerRepository.insert(mobileCustomer);
                megMap.put("msg","successfully add");
            }
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
    public Map<Object,GeoJsonPoint> queryAndUpdateLocation() {
//        查询出还未进行服务的用户
        Query query = new Query(Criteria.where("serviceStatus").is(Boolean.FALSE));
        List<MobileCustomer> mobileCustomers = mongoTemplate.find(query, MobileCustomer.class);
        List<Map<Object,Boolean>> mobileList = new LinkedList<>();
        Map<Object,GeoJsonPoint> mobileLocation = new HashMap<>();
        ArrayList<String> userList = new ArrayList<>();
//        存储位置
        for (MobileCustomer mobileCustomer:mobileCustomers){
            Map<Object,Boolean> statusMap = new HashMap<>();
            statusMap.put(mobileCustomer.getMobileId(),mobileCustomer.getServiceStatus());
            userList.add(mobileCustomer.getMobileId());
            mobileList.add(statusMap);
        }
        for (String list:userList) {
            System.out.println(list);
//            查询有基准点的用户
            Query queryLocation = new Query(Criteria.where("mobileId").is(list));
            queryLocation.fields().elemMatch("customerLocation", Criteria.where("logicStatus").is(1));
            queryLocation.fields().include("mobileId");
            MobileCustomer one = mongoTemplate.findOne(queryLocation, MobileCustomer.class);
            if (one.getCustomerLocation() != null) {
                System.out.println("_________not null___________");
                List<CustomerLocation> customerLocation1 = one.getCustomerLocation();
                mobileLocation.put(one.getMobileId(), customerLocation1.get(0).getGeoPoint());
//                System.out.println(tempLocation.getGeoPoint());
            } else {
                System.out.println("-------------null-------------");
//                查询时间最早的点，没有基准点的用户
                Query queryTime = new Query(Criteria.where("mobileId").is(list));
                queryTime.fields().elemMatch("customerLocation",Criteria.where("logicStatus").is(0));
                queryTime.fields().include("mobileId");
                MobileCustomer one1 = mongoTemplate.findOne(queryTime, MobileCustomer.class);
                GeoJsonPoint geoPoint = one1.getCustomerLocation().get(0).getGeoPoint();
                mobileLocation.put(one1.getMobileId(),geoPoint);
//                System.out.println(one1);
            }

        }
        System.out.println(mobileLocation);
//        根据坐标点进行计算，判断坐标是否超出
//        改变代码逻辑，每次都是新的点与基准点进行计算
        for (String list:userList){
//            System.out.println(mobileLocation.get(list));
            System.out.println(list);
//            每次取到最新的值
            Aggregation aggregation = newAggregation(MobileCustomer.class,
                    unwind("customerLocation"),
                    match(Criteria.where("mobileId").is(list)),
                    sort(DESC,"customerLocation.serviceTime"),
//                    project("mobileId","serviceStatus","customerLocation.geoPoint","customerLocation.serviceTime"),
                    project("customerLocation.geoPoint","mobileId"),
                    limit(1)
                    );
//            得到最新坐标
            AggregationResults<AggrMobileResults> aggregateResult = mongoTemplate.aggregate(aggregation,"mobileCustomer", AggrMobileResults.class);
            System.out.println(aggregateResult.getMappedResults());
            AggrMobileResults target = aggregateResult.getMappedResults().get(0);
//            计算两个点之间的距离
            GlobalCoordinates targetCoord = new GlobalCoordinates(target.getGeoPoint().getX(), target.getGeoPoint().getY());
            GlobalCoordinates sourceCoord = new GlobalCoordinates(mobileLocation.get(list).getX(), mobileLocation.get(list).getY());
            double distance = commonUtilsService.calculateGeoPointsDistance(sourceCoord, targetCoord, Ellipsoid.WGS84);
            System.out.println("juli "+distance);
//            此处计算两个点的距离，并进行判断，两个点不能超过50m
//            如果超过50m，革新点,得到新的坐标点，返回前端，返回mysql，重新计算
            if (distance >= 50){
//                重新设置规划数据
                mobileLocation.replace(list,target.getGeoPoint());
//                抛弃所有status为0的坐标，将这些坐标status都设置为2
                updateOneUsersLogicStatus(list);
//                设置唯一的一个坐标状态为1
                setOneUserLogicStatus(target.getGeoPoint());
            }
        }
        System.out.println(mobileLocation);
//        将更新的坐标集合发送出去
        return mobileLocation;
//        return mobileList;
    }
//    将所有用户的logic状态更新为废弃状态2
    @Override
    public UpdateResult updateOneUsersLogicStatus(String userId) {
//        将所有大于0小于1的数据全部设置为2
        Query query = new Query(Criteria.where("customerLocation.userId").is(userId).and("customerLocation.logicStatus").gte(0).lte(1));
        AggregationUpdate aggregationUpdate = newUpdate();
        aggregationUpdate.set("customerLocation.logicStatus").toValue(2);
        //        System.out.println(all);
        return mongoTemplate.update(MobileCustomer.class).matching(query).apply(aggregationUpdate).all();
    }

    @Override
    public void setOneUserLogicStatus(GeoJsonPoint jsonPoint) {
        Query query = new Query(Criteria.where("customerLocation.geoPoint").is(jsonPoint));
        AggregationUpdate aggregationOne = newUpdate();
        aggregationOne.set("customerLocation.logicStatus");
        UpdateResult result = mongoTemplate.update(MobileCustomer.class).matching(query).apply(aggregationOne).all();
        System.out.println(result);
    }

}
