package com.mountain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mountain.entity.Location;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.util.ArrayList;
import java.util.Map;


public interface LocationService extends IService<Location> {
    // 固定VRP算法
    Map<Object, ArrayList<Integer>> planCompute(Long[][] distanceMatrix, Integer vehicleNumber, Integer depot);
    // 动态VRP算法
    Map<Object, ArrayList<Integer>> movePassengerPlan(Long[][] tempDistanceMatrix, Integer vehicleNumber , int[] startPosition, int[] depot);
    // 动态保存用户位置
    Boolean dynamicLocationSave(Map<String, GeoJsonPoint> locationMap,Map<String,Location> uavLocation,Map<String,Location> depotLocation);
    Map<Object, ArrayList<Integer>> dynamicRoutes(Map<String,Location> uavLocation);
//    保存动态位置，单一测试
    Boolean saveAllUsers(Map<String, GeoJsonPoint> locationMap);

    /**
     * 查找index
     * @param name
     * @return
     */
    int findIndexByName(String name);

    /**
     * 查找所有未服务的用户
     * @return
     */
    int[] findUnservedUsers();

    Map<String, Object> findStaticRoutes(int vehicleNum);
    void deleteAllLocation();
    int existD


}
