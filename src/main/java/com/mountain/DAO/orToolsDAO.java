package com.mountain.DAO;

import com.google.ortools.constraintsolver.Assignment;
import com.google.ortools.constraintsolver.RoutingIndexManager;
import com.google.ortools.constraintsolver.RoutingModel;
import com.mountain.entity.Location;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.logging.Logger;

@Repository
public class orToolsDAO {
    private static final Logger logger = Logger.getLogger(orToolsDAO.class.getName());
//    地球半径距离
    private static final double EARTH_RADIUS = 6371393;
//    计算平面上两个点之间的直线距离，并返回一个距离矩阵，并返回一个long类型的距离矩阵
    public Long[][] computeDistance(Double[][] locations){
        Long[][] distanceMatrix=new Long[locations.length][locations.length];
        for(int rowIndex=0;rowIndex<locations.length;rowIndex++){
            for (int colIndex=0;colIndex<locations.length;colIndex++){
                if (rowIndex==colIndex){
                    distanceMatrix[colIndex][rowIndex]=0L;
                }else {
//                    不是两两相减，而是与第一个元素相减
//                    distanceMatrix[rowIndex][colIndex]=locations[colIndex]-locations[rowIndex];
//                    获取两个数的平方和，即欧几里得距离
                    double radiansAX= Math.toRadians(locations[colIndex][0]);//A点经度
                    double radiansAY= Math.toRadians(locations[colIndex][1]);//A点维度
                    double radiansBX= Math.toRadians(locations[rowIndex][0]);//B点经度
                    double radiansBY= Math.toRadians(locations[rowIndex][1]);//B点维度
                    double cos = (Math.cos(radiansAY) * Math.cos(radiansBY) * Math.cos(radiansAX - radiansBX) + Math.sin(radiansAY) * Math.sin(radiansBY));
////        System.out.println("cos = " + cos); // 值域[-1,1]
                    double acos = Math.acos(cos); // 反余弦值
//                    由于ortools的限制，需要采取缩放保证数值精确度，
//                    将所有距离值乘以10，然后取整，使得精度提升至分米级
                    double dis= (EARTH_RADIUS*acos*10);
                    distanceMatrix[rowIndex][colIndex]=Math.round(dis);
//                    System.out.println("距离为 "+dis+" 分米");
                }
            }
        }
//        打印调试
        for (Long[] distance:distanceMatrix){
            System.out.println("打印距离矩阵："+ Arrays.toString(distance));
        }
        return distanceMatrix;
    }
//    打印求解的结果
    public Map<Integer, ArrayList<Integer>> printSolution(int vehicleNumber, RoutingModel routing, RoutingIndexManager manager, Assignment solution) {
        Map<Integer, ArrayList<Integer>> routeMap=new HashMap<>();
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
            logger.info("Distance of the route: " + routeDistance + "dm");
            maxRouteDistance = Math.max(routeDistance, maxRouteDistance);
        }
        logger.info("Maximum of the route distances: " + maxRouteDistance/10 + "m");

//        System.out.println(routeMap);
//        返回计算路线结果集
        return routeMap;
    }
//    根据查到的坐标信息，返回一个纯数组形式的坐标数组
    public Double[][] locationMatrix(List<Location> list) {
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
        return locaList;
    }

}
