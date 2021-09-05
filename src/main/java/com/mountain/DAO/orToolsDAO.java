package com.mountain.DAO;

import org.springframework.stereotype.Repository;

@Repository
public class orToolsDAO {
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
                    double radiansAX=Math.toRadians(locations[colIndex][0]);//A点经度
                    double radiansAY=Math.toRadians(locations[colIndex][1]);//A点维度
                    double radiansBX=Math.toRadians(locations[rowIndex][0]);//B点经度
                    double radiansBY=Math.toRadians(locations[rowIndex][1]);//B点维度

                    distanceMatrix[rowIndex][colIndex]=Math.hypot(locations[colIndex][0]-locations[rowIndex][0],locations[colIndex][1]-locations[rowIndex][1]);
                }

            }
        }
        return distanceMatrix;
    }

}
