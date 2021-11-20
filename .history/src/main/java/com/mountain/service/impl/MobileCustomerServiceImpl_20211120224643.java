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
import org.springframework.data.domain.Sort;
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

import java.time.LocalDateTime;
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
            megMap.put("status",404);
            megMap.put("msg","something wrong happened");
        }
        return megMap;
    }

    @Override
    public Map<String,Object> saveCustomerLocation(CustomerLocation customerLocation) {
        Map<String,Object> megMap = new HashMap<>();
        String userId = customerLocation.getUserid();
        Query query = new Query(Criteria.where("mobileId").is(userId));
        MobileCustomer verifyOne = mongoTemplate.findOne(query, MobileCustomer.class);
        if (verifyOne == null){
            megMap.put("msg", "the user does not exist, checkin");
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
        mongoTemplate.dropCollection(MobileCustomer.class);
        return "success";
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
    public Map<String,Object> saveManyMobileUsers(List<MobileCustomer> customerList) {
        Map<String,Object> megMap = new HashMap<>();
        try {
            Collection<MobileCustomer> insert = mongoTemplate.insert(customerList, MobileCustomer.class);
            megMap.put("msg","successfully add");
            megMap.put("status",200);
        }catch (Exception e){
            System.out.println(e);
            megMap.put("msg","failure");
            megMap.put("status",400);
        }
        return megMap;

    }

    @Override
    public Map<String, Object> updateManyUsersLocation(List<CustomerLocation> customerLocationList) {
        Map<String,Object> megMap = new HashMap<>();
//        CustomerLocation tempLocation = new CustomerLocation();
        try {
            for (CustomerLocation customerLocation : customerLocationList) {
                Query query = new Query(Criteria.where("mobileId").is(customerLocation.getUserid()).and("uuid").is(customerLocation.getUuid()));
//            管他是不是0，全设置为0
                customerLocation.setLogicStatus(customerLocation.getLogicStatus() != 0 ? 0 : 0);
                MobileCustomer verifyOne = mongoTemplate.findOne(query, MobileCustomer.class);
                if (verifyOne == null) {
//                将添加失败的数组返回至前端
                    megMap.put(customerLocation.getUserid(), "the user does not exist, checkin");
                } else {
//                    当存在用户和同一uuid则插入数据
                    Query queryUsers = new Query(Criteria.where("mobileId").is(customerLocation.getUserid()).and("uuid").is(customerLocation.getUuid()));
                    Update update = new Update();
                    update.push("customerLocation",customerLocation);
                    mongoTemplate.upsert(queryUsers, update, MobileCustomer.class);
                    megMap.put("msg","successfully insert");
                }
            }
            megMap.put("status",200);
        }catch (Exception e){
            System.out.println(e);
            megMap.put("status",400);
            megMap.put("msg",e.getMessage());
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

//    查询出用户的当前状态信息并更新
    @Override
    public Map<Object,Object> queryAndUpdateLocation(String uuid) {
        Map<Object,Object> msgMap = new HashMap<>();
        boolean dynamicSta = false;
//        查询出还未进行服务的用户,通过uuid确定唯一的用户
        Query query = new Query(Criteria.where("serviceStatus").is(Boolean.FALSE).and("uuid").is(uuid));
        List<MobileCustomer> mobileCustomers = mongoTemplate.find(query, MobileCustomer.class);
        // List<Map<Object,Boolean>> mobileList = new LinkedList<>();
        Map<Object,GeoJsonPoint> mobileLocation = new HashMap<>();
        ArrayList<String> userList = new ArrayList<>();
//        存储位置
        for (MobileCustomer mobileCustomer:mobileCustomers){
            // Map<Object,Boolean> statusMap = new HashMap<>();
            // statusMap.put(mobileCustomer.getMobileId(),mobileCustomer.getServiceStatus());
            userList.add(mobileCustomer.getMobileId());
            // mobileList.add(statusMap);
        }
//        System.out.println(userList);
        // 查询并更新基准点
        for (String list:userList) {
            System.out.println("user "+list);
//            查询有基准点的用户，即logic为1,确保每次查找的都是一个uuid
            Query queryLocation = new Query(Criteria.where("mobileId").is(list).and("uuid").is(uuid));
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
                Query queryTime = new Query(Criteria.where("mobileId").is(list).and("uuid").is(uuid));
                queryTime.fields().elemMatch("customerLocation",Criteria.where("logicStatus").is(0));
                queryTime.fields().include("mobileId");
                MobileCustomer one1 = mongoTemplate.findOne(queryTime, MobileCustomer.class);
//                System.out.println(one1);
                // if (one1.getCustomerLocation() != null){
                GeoJsonPoint geoPoint = one1.getCustomerLocation().get(0).getGeoPoint();
                mobileLocation.put(one1.getMobileId(),geoPoint);
//                System.out.println(mobileLocation);
                // } else{
                //     System.out.println("geoPoint saty the same");
                //     mobileLocation.put(one1.getMobileId(), value)
                // }
            }

        }
//        根据坐标点进行计算，判断坐标是否超出
//        改变代码逻辑，每次都是新的点与基准点进行计算
        for (String list:userList){
//            System.out.println(mobileLocation.get(list));
            System.out.println(list);
//            每次取到最新的值
            Aggregation aggregation = newAggregation(MobileCustomer.class,
                    unwind("customerLocation"),
                    match(Criteria.where("mobileId").is(list).and("uuid").is(uuid)),
                    sort(DESC,"customerLocation.serviceTime"),
//                    project("mobileId","serviceStatus","customerLocation.geoPoint","customerLocation.serviceTime"),
                    project("mobileId","customerLocation.geoPoint","customerLocation.serviceTime"),
                    limit(1)
                    );
//            得到最新坐标,将结果输出值新的AggrMobileResults实体类中
            AggregationResults<AggrMobileResults> aggregateResult = mongoTemplate.aggregate(aggregation,"mobileCustomer", AggrMobileResults.class);
            AggrMobileResults target = aggregateResult.getMappedResults().get(0);
            System.out.println(target);
//            计算两个点之间的距离
            GlobalCoordinates targetCoord = new GlobalCoordinates(target.getGeoPoint().getX(), target.getGeoPoint().getY());
            GlobalCoordinates sourceCoord = new GlobalCoordinates(mobileLocation.get(list).getX(), mobileLocation.get(list).getY());
            double distance = commonUtilsService.calculateGeoPointsDistance(sourceCoord, targetCoord, Ellipsoid.WGS84);
            System.out.println("历史点之间的距离 "+distance);
//            此处计算两个点的距离，并进行判断，两个点不能超过50m
//            如果超过50m，革新点,得到新的坐标点，返回前端，返回mysql，重新计算
            if (distance >= 50){
//                重新设置规划数据
                mobileLocation.replace(list,target.getGeoPoint());
//                抛弃所有status为0的坐标，将这些坐标status都设置为2
                updateOneUsersLogicStatus(list,uuid);
//                根据用户id设置唯一的一个坐标状态为1,再加入时间，保证唯一性
                setOneUserLogicStatus(list,target.getServiceTime(),target.getGeoPoint());
//                存在动态规划的点
                dynamicSta = true;
            }
        }
        // System.out.println(mobileLocation);
//        将更新的坐标集合发送出去
        if (dynamicSta){
            msgMap.put("status",200);
            msgMap.put("planning","planning changed");
            msgMap.put("locations",mobileLocation);
        }else {
            msgMap.put("status",201);
            msgMap.put("planning","nothing changed");
            msgMap.put("locations",mobileLocation);
        }
        return msgMap;
//        return mobileList;
    }
//    将所有用户的logic状态更新为废弃状态2
    @Override
    public UpdateResult updateOneUsersLogicStatus(String userId,String uuid) {
//        将所有大于0小于1的数据全部设置为2
        Query query = new Query(Criteria.where("customerLocation.userId").is(userId).and("customerLocation.logicStatus").gte(0).lte(1).and("uuid").is(uuid));
        AggregationUpdate aggregationUpdate = newUpdate();
        aggregationUpdate.set("customerLocation.logicStatus").toValue(2);
        //        System.out.println(all);
        UpdateResult result = mongoTemplate.update(MobileCustomer.class).matching(query).apply(aggregationUpdate).all();
        System.out.println("all set to 2");
        System.out.println(result);
        return result;
    }
//    确保至少一个用户的逻辑状态为1
    @Override
    public void setOneUserLogicStatus(String userId,LocalDateTime moveTimeStamp,GeoJsonPoint jsonPoint) {
        // 根据id和用户坐标同时设置
        System.out.println(moveTimeStamp);
        Query query = new Query(Criteria.where("mobileId").is(userId).and("customerLocation.geoPoint").is(jsonPoint).and("customerLocation.serviceTime").is(moveTimeStamp));
        // AggregationUpdate aggregationOne = newUpdate();
        // aggregationOne.set("customerLocation.logicStatus").toValue(1);
        // UpdateResult result = mongoTemplate.update(MobileCustomer.class).matching(query).apply(aggregationOne).all();
        // 匹配项logic status 设置为1
        Update update = new Update();
        update.set("customerLocation.$.logicStatus", 1);
        UpdateResult rls = mongoTemplate.updateFirst(query, update, MobileCustomer.class);
        System.out.println("update ONE: ");
        System.out.println(rls);
        // System.out.println(result);
    }

    @Override
    public Map<String,Object> findNewestUUID() {
        Map<String,Object> megMap = new HashMap<>();
        Query query = new Query();
        Sort sort = Sort.by("createTime").descending();
        MobileCustomer one = mongoTemplate.findOne(query.with(sort).limit(1), MobileCustomer.class);
        megMap.put("msg","success");
        megMap.put("status",200);
        assert one != null;
        megMap.put("results",one.getUuid());
        return megMap;
    }


    /**
     * 查找对象
     */
    @Override
    public Boolean findUUIDBoolean(String uuid) {
        Query query = new Query(Criteria.where("uuid").is(uuid));
        return mongoTemplate.exists(query, MobileCustomer.class);
    
    }

}
