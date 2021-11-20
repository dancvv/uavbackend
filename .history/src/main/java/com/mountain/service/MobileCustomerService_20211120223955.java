package com.mountain.service;

import com.mongodb.client.result.UpdateResult;
import com.mountain.entity.CustomerLocation;
import com.mountain.entity.MobileCustomer;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @description: service
 * @author: mountainINblack
 * @date: 2021/10/30 17:20
 */
public interface MobileCustomerService {
    Map<String,Object> insertMobileUsers(MobileCustomer mobileCustomer);
    Map<String,Object> saveCustomerLocation(CustomerLocation customerLocation);
    String deleteByUserId(String id);
    String deleteAllUsers();
    String removeLocationByUserId(String id);
    MobileCustomer findByUersId(String id);
    Map<String,Object> saveManyMobileUsers(List<MobileCustomer> customerList);
    Map<String,Object> updateManyUsersLocation(List<CustomerLocation> customerLocationList);
    List<MobileCustomer> LocationCompare();
    List<MobileCustomer>  findUserStatus(Boolean status);
    Map<Object,Object> queryAndUpdateLocation(String uuid);
    UpdateResult updateOneUsersLogicStatus(String userId,String uuid);
    void setOneUserLogicStatus(String userId, LocalDateTime moveTimeStamp, GeoJsonPoint jsonPoint);
    Map<String,Object> findNewestUUID();
    Boolean findBoolean
}
