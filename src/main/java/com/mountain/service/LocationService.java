package com.mountain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mountain.entity.Location;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.util.ArrayList;
import java.util.Map;


public interface LocationService extends IService<Location> {
    Map<Object, ArrayList<Integer>> planCompute(Long[][] distanceMatrix, Integer vehicleNumber, Integer depot);
    Map<Object, ArrayList<Integer>> movePassengerPlan(Long[][] tempDistanceMatrix, Integer vehicleNumber , int[] startPosition, int[] depot);
    void dynamicLocationSave(Map<String, GeoJsonPoint> locationMap);
    void dynamicLocation();


}
