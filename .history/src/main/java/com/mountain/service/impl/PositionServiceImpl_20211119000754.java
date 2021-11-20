package com.mountain.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.ortools.Loader;
import com.google.ortools.constraintsolver.*;
import com.mountain.DAO.orToolsDAO;
import com.mountain.Mapper.LocalMapper;
import com.mountain.entity.Location;
import com.mountain.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PositionServiceImpl extends ServiceImpl<LocalMapper, Location> implements LocationService {

    @Autowired
    private orToolsDAO ordao;

    @Autowired
    private LocalMapper localMapper;
//    抽离共同代码

    public Assignment computeService(RoutingIndexManager manager,Long[][] distanceMatrix, Integer vehicleNumber,RoutingModel routing){
        // Create Routing Model.
//        RoutingModel routing = new RoutingModel(manager);

        // Create and register a transit callback.
        final int transitCallbackIndex =
                routing.registerTransitCallback((long fromIndex, long toIndex) -> {
                    // Convert from routing variable Index to user NodeIndex.
                    int fromNode = manager.indexToNode(fromIndex);
                    int toNode = manager.indexToNode(toIndex);
                    return distanceMatrix[fromNode][toNode];
                });

        // Define cost of each arc.
        routing.setArcCostEvaluatorOfAllVehicles(transitCallbackIndex);

        // Add Distance constraint.
        // 无人机最大飞行距离设置为8km
        routing.addDimension(transitCallbackIndex,
                0,
                80000,
                true, // start cumul to zero
                "Distance");
        RoutingDimension distanceDimension = routing.getMutableDimension("Distance");
        distanceDimension.setGlobalSpanCostCoefficient(100);

        // Setting first solution heuristic.
        RoutingSearchParameters searchParameters =
                main.defaultRoutingSearchParameters()
                        .toBuilder()
                        .setFirstSolutionStrategy(FirstSolutionStrategy.Value.PATH_CHEAPEST_ARC)
                        .build();

        // Solve the problem.
        Assignment solution = routing.solveWithParameters(searchParameters);

        // Print solution on console.
        return solution;
    }
//    VRP路线规划
    @Override
    public Map<Object, ArrayList<Integer>> planCompute(Long[][] distanceMatrix, Integer vehicleNumber, Integer depot){
//        System.load("/home/ubuntu/.m2/repository/com/google/ortools/ortools-linux-x86-64/9.0.9048/linux-x86-64/libjniortools.so");
        Loader.loadNativeLibraries();

        // Create Routing Index Manag er
        RoutingIndexManager manager =
                new RoutingIndexManager( distanceMatrix.length,  vehicleNumber,  depot);

////        // Create Routing Model.
        RoutingModel routing = new RoutingModel(manager);
////
//        // Create and register a transit callback.
//        final int transitCallbackIndex =
//                routing.registerTransitCallback((long fromIndex, long toIndex) -> {
//                    // Convert from routing variable Index to user NodeIndex.
//                    int fromNode = manager.indexToNode(fromIndex);
//                    int toNode = manager.indexToNode(toIndex);
//                    return distanceMatrix[fromNode][toNode];
//                });
//
//        // Define cost of each arc.
//        routing.setArcCostEvaluatorOfAllVehicles(transitCallbackIndex);
//
//        // Add Distance constraint.
//        // 无人机最大飞行距离设置为8km
//        routing.addDimension(transitCallbackIndex,
//                0,
//                80000,
//                true, // start cumul to zero
//                "Distance");
//        RoutingDimension distanceDimension = routing.getMutableDimension("Distance");
//        distanceDimension.setGlobalSpanCostCoefficient(100);
//
//        // Setting first solution heuristic.
//        RoutingSearchParameters searchParameters =
//                main.defaultRoutingSearchParameters()
//                        .toBuilder()
//                        .setFirstSolutionStrategy(FirstSolutionStrategy.Value.PATH_CHEAPEST_ARC)
//                        .build();
//
//        // Solve the problem.
//        Assignment solution = routing.solveWithParameters(searchParameters);
//
//        // Print solution on console.
//        return ordao.printSolution(vehicleNumber, routing, manager, solution);
        Assignment solution = computeService(manager, distanceMatrix, vehicleNumber,routing);
        return ordao.printSolution(vehicleNumber, routing, manager, solution);
    }
//    MDVRP路线规划,多场站路线规划
    @Override
    public Map<Object, ArrayList<Integer>> movePassengerPlan(Long[][] tempDistanceMatrix, Integer vehicleNumber , int[] startPosition, int[] depot){
        Loader.loadNativeLibraries();
        RoutingIndexManager mdVRPmanager=new RoutingIndexManager(tempDistanceMatrix.length,
                vehicleNumber,
                startPosition,
                depot);
//        computeService(mdVRPmanager,tempDistanceMatrix,vehicleNumber,);
        RoutingModel planRouting = new RoutingModel(mdVRPmanager);
//
//        final int transitCallbackIndex=planRouting.registerTransitCallback((long fromIndex,long toIndex)->{
//            int fromNode=mdVRPmanager.indexToNode(fromIndex);
//            int toNode=mdVRPmanager.indexToNode(toIndex);
//            return tempDistanceMatrix[fromNode][toNode];
//        });
//        planRouting.setArcCostEvaluatorOfAllVehicles(transitCallbackIndex);
//        planRouting.addDimension(transitCallbackIndex,0,6000,true,"Distance");
//        RoutingDimension mutableDimension = planRouting.getMutableDimension("Distance");
//        mutableDimension.setGlobalSpanCostCoefficient(100);
//        RoutingSearchParameters routingSearchParameters = main.defaultRoutingSearchParameters()
//                .toBuilder()
//                .setFirstSolutionStrategy(FirstSolutionStrategy.Value.PATH_CHEAPEST_ARC)
//                .build();
//        Assignment solution = planRouting.solveWithParameters(routingSearchParameters);
//        return ordao.printSolution(vehicleNumber,planRouting,mdVRPmanager,solution);
        Assignment solution=computeService(mdVRPmanager,tempDistanceMatrix,vehicleNumber,planRouting);
        return ordao.printSolution(vehicleNumber,planRouting,mdVRPmanager,solution);
    }

//    保存动态用户的位置
    @Override
    public Boolean dynamicLocationSave(Map<String, GeoJsonPoint> locationMap,Map<String,Location> uavLocation,Map<String,Location> depotLocation) {
        List<Location> locationCollection = new ArrayList<>();
        // 保存用户位置
        locationMap.forEach((k,v) -> {
            Location mobilePositionSet = new Location();
            mobilePositionSet.setMobileid(k);
            mobilePositionSet.setLat(v.getX());
            mobilePositionSet.setLng(v.getY());
            locationCollection.add(mobilePositionSet);
        });
        // 保存无人机位置
        uavLocation.forEach((K,V) -> {
            Location uavPositionSet = new Location();
            uavPositionSet.setMobileid(V.getMobileid());
            uavPositionSet.setLat(V.getLat());
            uavPositionSet.setLng(V.getLng());
            locationCollection.add(uavPositionSet);
        });
//        保存仓库位置
        depotLocation.forEach((K,V) ->{
            Location depotPositionSet = new Location();
            depotPositionSet.setMobileid(V.getMobileid());
            depotPositionSet.setLat(V.getLat());
            depotPositionSet.setLng(V.getLng());
            locationCollection.add(depotPositionSet);
        });
        // 批量保存用户
        return saveBatch(locationCollection);
    }
//    调用规划算法
    @Override
    public Map<Object, ArrayList<Integer>> dynamicRoutes(Map<String,Location> uavLocation) {
//        查询有多少条数据
        int count = (int) count();
//        无人机数量
        int uavSize = uavLocation.size();
        int[] startPosition = new int[uavSize];
        int[] stopPosition = new int[uavSize];
//            起始站点
        for (int i = 0; i<uavSize; i++){
            startPosition[i] = count - uavSize -1 +i;
        }
//        仓储位置
        for (int i = 0; i<uavSize; i++){
            stopPosition[i] = count - 1;
        }
//        规划算法
        List<Location> allLocationList = list();
        Double[][] locationMatrix = ordao.locationMatrix(allLocationList);
        Long[][] distanceMatrix = ordao.computeDistance(locationMatrix);
//        得到规划路线
        Map<Object, ArrayList<Integer>> objectArrayListMap = movePassengerPlan(distanceMatrix, uavSize, startPosition, stopPosition);
        return objectArrayListMap;
    }
//    保存所有的用户位置
    @Override
    public void saveOneListUsers(Map<String, GeoJsonPoint> locationMap) {
//        刪除所有位置信息
        localMapper.deleteAllLocations();
        List<Location> locationCollection = new ArrayList<>();
        // 保存用户位置
        locationMap.forEach((k,v) -> {
            Location mobilePositionSet = new Location();
            mobilePositionSet.setMobileid(k);
            mobilePositionSet.setLat(v.getX());
            mobilePositionSet.setLng(v.getY());
            locationCollection.add(mobilePositionSet);
        });
        saveBatch(locationCollection);
    }

    
    @Override
    public int findIndexByName(String name) {
        return localMapper.findIndexByName(name);
    }
    @Override
    public int[] findUnservedUsers() {
        return localMapper.findUsers();
    }


}
