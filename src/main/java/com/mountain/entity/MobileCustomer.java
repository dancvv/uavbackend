package com.mountain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

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
    @Indexed(unique = true)
    private String mobileId;
    private String serviceStatus;
//    用户加入时间
    private Date createTime;
    private List<CustomerLocation> customerLocation;
}
