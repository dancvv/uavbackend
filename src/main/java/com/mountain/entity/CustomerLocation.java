package com.mountain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.util.Date;

/**
 * @description:
 * @author: mountainINblack
 * @date: 2021/10/30 16:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerLocation {
    private String user_id;
//    当前服务状态：1：服务，0：未服务
    private Boolean status;
//    服务时间
    private Date service_time;
    private GeoJsonPoint jsonPoint;

}
