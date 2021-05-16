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
//    平均路长
    private Double AverageRoute;
//    总路长
    private Double TotalRoute;
//    运行中的BUS
    private int RunningBus;
//    可用的BUS
    private int AvailableBus;
//    现有的BUS
    private int ExistingBus;
}
