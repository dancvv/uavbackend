package com.mountain.entity;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Location {
//    存储地理位置经纬度
    public  double lat;
    private double lng;
}
