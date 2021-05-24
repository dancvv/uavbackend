package com.mountain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusCompany {
//    运行期间车上的人数
    private int PassengerInBus;
//    车站等车的人数
    private int PassengerWaitInStation;
//    每个乘客的平均路长
    private double AveRoutePassengerLength;
//    平均路长
    private double AverageRoute;
//    总路长
    private double TotalRoute;
//    运行中的BUS
    private int RunningBus;
//    可用的BUS
    private int AvailableBus;
//    现有的BUS
    private int ExistingBus;
}

// station
