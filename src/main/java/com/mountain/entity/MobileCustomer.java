package com.mountain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @description: 动态用户实体类设计
 * @author: mountainINblack
 * @date: 2021/10/28 20:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "mobileCustomer")
public class MobileCustomer {
    @Id
    private String id;
//    默认为唯一值
//    @Indexed(unique = true)
    private String mobileId;
//    当前用户的服务状态
    private Boolean serviceStatus;
//    用户加入时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(locale = "zh",pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime createTime;
    private List<CustomerLocation> customerLocation;
}
