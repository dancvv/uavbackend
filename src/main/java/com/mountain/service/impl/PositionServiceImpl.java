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
import java.util.logging.Logger;
@Service
public class PositionServiceImpl extends ServiceImpl<LocaMapper, Location> implements LocationService {

    @Autowired
    private orToolsDAO ordao;
//    VRP路线规划
    public Map<Integer, ArrayList<Integer>> planCompute(Long[][] distanceMatrix, Integer vehicleNumber, Integer depot){
        Loader.loadNativeLibraries();

        // Create Routing Index Manager
        RoutingIndexManager manager =
                new RoutingIndexManager( distanceMatrix.length,  vehicleNumber,  depot);

        // Create Routing Model.
        RoutingModel routing = new RoutingModel(manager);

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
        return ordao.printSolution(vehicleNumber, routing, manager, solution);
    }
//    MDVRP路线规划
    public
}
