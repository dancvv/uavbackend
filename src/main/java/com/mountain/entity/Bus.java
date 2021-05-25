package com.mountain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bus {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;
    private int busID;
//    公交实时位置
    private Location busLocation;
//    车上的乘客
    private int passengerInBus;
//    下一站上车的乘客
    private int passengerInNextStation;
//    下一站下车的乘客
    private int passengerLeaveNextStation;
//    公交座位数
    private int seat;
//    下一站
    private Stations nextStation;
//    上一站
    private Stations lastStation;
//    需要传入路线，路线以声明样的形式存储，存疑
//    Collection RouteLine = new ArrayList();


}
