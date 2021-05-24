package com.mountain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//存储各个站点信息
public class Stations {
    private int id;
    private String stationName;
    private Location stationLocation;
}
