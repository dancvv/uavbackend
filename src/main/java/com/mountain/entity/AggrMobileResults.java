package com.mountain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

/**
 * @description: 聚合输出类
 * @author: mountainINblack
 * @date: 2021/11/6 15:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AggrMobileResults {
    private String id;
    private String mobileId;
    private GeoJsonPoint geoPoint;
    private LocalDateTime serviceTime;    
}
