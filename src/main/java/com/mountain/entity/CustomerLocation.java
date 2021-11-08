package com.mountain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.time.LocalDateTime;
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
    private String userId;
//    当前服务状态：1：服务，0：未服务
    private Boolean status;
//    服务时间
    private LocalDateTime serviceTime;
//    服务逻辑判断，0：可搜索，1：搜索基准，2：废弃点，不参与搜索
    private int logicStatus;
//    @Indexed()
    private GeoJsonPoint geoPoint;

}
