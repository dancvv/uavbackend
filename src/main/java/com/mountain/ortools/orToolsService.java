package com.mountain.ortools;

import com.google.ortools.constraintsolver.RoutingIndexManager;
import com.google.ortools.constraintsolver.RoutingModel;

public class orToolsService {

    public void computeRoute(int length,int vehicleNumber,int depot){
        RoutingIndexManager manager=new RoutingIndexManager(length,vehicleNumber,depot);
        RoutingModel routing=new RoutingModel(manager);
//        final int transitCallbackIndex=routing.registerTransitCallback(long fromIndex,long toIndex)->{
////            转换路径变量索引为用户节点索引
//            int fromNode=manager.indexToNode(fromIndex);
//            int toNode=manager.indexToNode(toIndex);
//            return ;
//        };

    }
}
