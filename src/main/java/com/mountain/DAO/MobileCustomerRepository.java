package com.mountain.DAO;

import com.mountain.entity.MobileCustomer;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @description: Repository层，继承mongodb的方法
 * @author: mountainINblack
 * @date: 2021/10/30 17:07
 */

public interface MobileCustomerRepository extends MongoRepository<MobileCustomer,String> {
//    MobileCustomer findMobileCustomerByMobile_id(String id);
}
