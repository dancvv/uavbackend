package com.mountain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class InitPro {
    private int zoom;

    private Location centerPoint;
//    起始点
    private Location startPoint;
//    终止点
    private Location endPoint;

    private int waitPeople;

}
