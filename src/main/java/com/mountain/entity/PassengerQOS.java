package com.mountain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassengerQOS {
//    算法层面
    private Double AveWaitTime;
    private Double MaxWaitTime;
    private Double AvePassengerRouteLength;

}
