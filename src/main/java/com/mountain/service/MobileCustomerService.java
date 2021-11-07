package com.mountain.service;

import com.mongodb.client.result.UpdateResult;
import com.mountain.entity.CustomerLocation;
import com.mountain.entity.MobileCustomer;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

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
    Map<String,Object> insertManyMobileUsers(List<MobileCustomer> customerList);
    Map<String,Object> updateManyUsersLocation(List<CustomerLocation> customerLocationList);
    List<MobileCustomer> LocationCompare();
    List<MobileCustomer>  findUserStatus(Boolean status);
    void findEmbedDocument();
    UpdateResult updateOneUsersLogicStatus(String userId);
    void updateOneUserLogicStatus(GeoJsonPoint jsonPoint);
}
