package com.mountain.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Bus {
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
//    车辆中的路线
    private String route;

}
