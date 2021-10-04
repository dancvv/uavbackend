package com.mountain.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.ortools.Loader;
import com.google.ortools.constraintsolver.*;
import com.mountain.DAO.orToolsDAO;
import com.mountain.Mapper.LocaMapper;
import com.mountain.entity.Location;
import com.mountain.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class PositionServiceImpl extends ServiceImpl<LocaMapper, Location> implements LocationService {

    @Autowired
    private orToolsDAO ordao;
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
    public Map<Object, ArrayList<Integer>> planCompute(Long[][] distanceMatrix, Integer vehicleNumber, Integer depot){
        System.load("/home/r/.m2/repository/com/google/ortools/ortools-linux-x86-64/9.0.9048/linux-x86-64/libjniortools.so");
//        Loader.loadNativeLibraries();

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
//    MDVRP路线规划
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
}
