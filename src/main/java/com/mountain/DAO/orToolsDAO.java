package com.mountain.DAO;

import org.springframework.stereotype.Repository;

@Repository
public class orToolsDAO {
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
//                    将所有距离值乘以1000，然后进行取整
                    double dis= (EARTH_RADIUS*acos)*1000;
                    distanceMatrix[rowIndex][colIndex]=Math.round(dis);
                    System.out.println("距离为 "+dis+" 米");
                }

            }
        }
        return distanceMatrix;
    }

}
