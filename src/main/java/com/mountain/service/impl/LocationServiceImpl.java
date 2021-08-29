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

@Service
//不知道什么用的注解
//@Transactional
public class LocationServiceImpl extends ServiceImpl<LocaMapper, Location> implements LocationService {
//    计算矩阵值
    @Autowired
    private orToolsDAO orToolsDAO;
    static void printSolution(RoutingModel routing, RoutingIndexManager manager, Assignment solution){
//        检查解
        long maxRouteDistance=0;
    }
    public static void mainCompute(){
        Loader.loadNativeLibraries();
//        实例化数据问题
//        final DataModel data = new DataModel();
        int length=0;
        int vehicleNumber=4;
        int depot=0;
        RoutingIndexManager manager=new RoutingIndexManager(length,vehicleNumber,depot);
//        创建路线模型
        RoutingModel routing=new RoutingModel(manager);
//        创建并注册传输回调函数
        final int transitCallbackIndex = routing.registerTransitCallback((long fromIndex,long toIndex) ->{
//            将路线变量索引转换为用户节点索引
            int fromNode=manager.indexToNode(fromIndex);
            int toNode=manager.indexToNode(toIndex);
//            返回的是矩阵值
//            return data
            return fromIndex;
        });
        routing.setArcCostEvaluatorOfAllVehicles(transitCallbackIndex);
//        添加距离约束
        routing.addDimension(transitCallbackIndex,0,3000,
                true,//从零开始
                "Distance");
        RoutingDimension distanceDimension = routing.getMutableDimension("Distance");
        distanceDimension.setGlobalSpanCostCoefficient(100);
//        设置第一个解启发式
//        main为约束变量
        RoutingSearchParameters searchParameters = main.defaultRoutingSearchParameters()
                .toBuilder()
                .setFirstSolutionStrategy(FirstSolutionStrategy.Value.PATH_CHEAPEST_ARC)
                .build();
//        求解问题
        Assignment solution = routing.solveWithParameters(searchParameters);
//        打印求解
//        printSolution();

    }
}
