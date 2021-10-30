package com.mountain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * @description: 动态用户实体类设计
 * @author: mountainINblack
 * @date: 2021/10/28 20:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MobileCustomer {
    @Id
    private String id;
    private String mobile_id;
    private String service_status;
//    创建时间
    private Date create_time;
    private CustomerLocation customerLocation;
}
