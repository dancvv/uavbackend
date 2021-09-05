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
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.logging.Logger;

@Service
//不知道什么用的注解
//@Transactional
public class LocationServiceImpl extends ServiceImpl<LocaMapper, Location> implements LocationService {
    private static final Logger logger=Logger.getLogger(LocationServiceImpl.class.getName());
//    计算矩阵值
    @Autowired
    private orToolsDAO orToolsDAO;
    /// @brief Print the solution.
    static Map<Integer, ArrayList<Integer>> printSolution(int vehicleNumber, RoutingModel routing, RoutingIndexManager manager, Assignment solution) {
        Map<Integer, ArrayList<Integer>> routeMap=new HashMap<>();
        System.out.println(solution.objectiveValue());
        // Solution cost.
        logger.info("Objective : " + solution.objectiveValue());
        // Inspect solution.
        long maxRouteDistance = 0;
        for (int i = 0; i < vehicleNumber; ++i) {
//            从i个车出发的索引
            long index = routing.start(i);
//            System.out.println("测试"+index);
            logger.info("Route for Vehicle " + i + ":");
//            行驶距离
            long routeDistance = 0;
//            字符串
            String route = "";
//            System.out.println(index);
            ArrayList<Integer> arrayList=new ArrayList<>();
            arrayList.add(manager.indexToNode(index));
            while (!routing.isEnd(index)) {
//                保存站点数据至链表中
                route += manager.indexToNode(index) + " -> ";
//                前一个节点
//                当前节点
                long previousIndex = index;
//                下一节点
                index = solution.value(routing.nextVar(index));
//                添加循环中的每一个节点
                arrayList.add(manager.indexToNode(index));
                routeDistance += routing.getArcCostForVehicle(previousIndex, index, i);
            }
//            小循环结束后，给定终值
//            arrayList.add((int) manager.getEndIndex(i));
            routeMap.put(i,arrayList);
            logger.info(route + manager.indexToNode(index));
            logger.info("Distance of the route: " + routeDistance + "m");
            maxRouteDistance = Math.max(routeDistance, maxRouteDistance);
        }
        logger.info("Maximum of the route distances: " + maxRouteDistance + "m");

//        System.out.println(routeMap);
//        返回计算路线结果集
        return routeMap;
    }
//    Map<Integer, ArrayList<Integer>>
    public Map<Integer, ArrayList<Integer>> mainCompute(Long[][] distanceMatrix,int vehicleNumber,int depot){
        Loader.loadNativeLibraries();
        // Instantiate the data problem.
        final VrpGlobalSpan.DataModel data = new VrpGlobalSpan.DataModel();



        // Create Routing Index Manager
        RoutingIndexManager manager = new RoutingIndexManager(distanceMatrix.length, vehicleNumber,depot);

        // Create Routing Model.
        RoutingModel routing = new RoutingModel(manager);

        // Create and register a transit callback.
        final int transitCallbackIndex =
                routing.registerTransitCallback((long fromIndex, long toIndex) -> {
                    // Convert from routing variable Index to user NodeIndex.
                    int fromNode = manager.indexToNode(fromIndex);
                    int toNode = manager.indexToNode(toIndex);
                    return fromNode;
                });

        // Define cost of each arc.
        routing.setArcCostEvaluatorOfAllVehicles(transitCallbackIndex);

        // Add Distance constraint.
        routing.addDimension(transitCallbackIndex, 0, 3000,
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
        return printSolution(vehicleNumber, routing, manager, solution);
    }
/*
    生成距离矩阵，可自定义
 */
    public Long[][] distanceCompute(List<Location> list) {
        //        生成路线矩阵
        Double [][]locaList = new Double[list.size()][2];
        int i=0;
        for (Location location : list) {
            locaList[i][0]=location.getLng();
            locaList[i][1]=location.getLat();
            i++;
        }
//        打印矩阵
//        for (Double[] loca : locaList) {
//            System.out.println("行矩阵：" + Arrays.toString(loca));
//        }
//        调用dao层计算距离
        final Long[][] distances = orToolsDAO.computeDistance(locaList);
        for (Long[] distance:distances){
            System.out.println("打印距离矩阵："+Arrays.toString(distance));
        }
        return distances;
    }
}
