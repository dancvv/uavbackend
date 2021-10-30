package com.mountain.DAO;

import com.mountain.entity.MobileCustomer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @description: Repository层，继承mongodb的方法
 * @author: mountainINblack
 * @date: 2021/10/30 17:07
 */
@Repository
public interface MobileCustomerRepository extends MongoRepository<MobileCustomer,String> {
}
