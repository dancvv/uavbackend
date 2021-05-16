package com.mountain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class InitPro {
    private int zoom;
    private Double centerLat;
    private Double centerLng;
//    起始点
    private Double StartPlat;
    private Double StartPlng;
//    终止点
    private Double endPlat;
    private Double endPlng;

    private int waitPeople;

}
