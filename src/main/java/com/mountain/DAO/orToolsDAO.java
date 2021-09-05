package com.mountain.DAO;

import org.springframework.stereotype.Repository;

@Repository
public class orToolsDAO {
//    地球半径距离
    private static final double EARTH_RADIUS = 6371393;
//    计算平面上两个点之间的直线距离，并返回一个距离矩阵
    public Double[][] distance(Double[][] locations){
        Double[][] distanceMatrix=new Double[locations.length][locations.length];
        for(int rowIndex=0;rowIndex<locations.length;rowIndex++){
            for (int colIndex=0;colIndex<locations.length;colIndex++){
                if (rowIndex==colIndex){
                    distanceMatrix[colIndex][rowIndex]=0.0;
                }else {
//                    不是两两相减，而是与第一个元素相减
//                    distanceMatrix[rowIndex][colIndex]=locations[colIndex]-locations[rowIndex];
//                    获取两个数的平方和，即欧几里得距离
                    double radiansAX= Math.toRadians(locations[colIndex][0]);//A点经度
                    double radiansAY= Math.toRadians(locations[colIndex][1]);//A点维度
                    double radiansBX= Math.toRadians(locations[rowIndex][0]);//B点经度
                    double radiansBY= Math.toRadians(locations[rowIndex][1]);//B点维度
                    double cos = (Math.cos(radiansAY) * Math.cos(radiansBY) * Math.cos(radiansAX - radiansBX)
                                                + Math.sin(radiansAY) * Math.sin(radiansBY));
////        System.out.println("cos = " + cos); // 值域[-1,1]
                    double acos = Math.acos(cos); // 反余弦值
                    double dis= (EARTH_RADIUS*acos);
                    distanceMatrix[rowIndex][colIndex]=dis;
                    System.out.println("距离为 "+dis+" 米");
                }

            }
        }
        return distanceMatrix;
    }

}
