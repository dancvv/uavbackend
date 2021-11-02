package com.mountain;

import com.mountain.entity.MobileCustomer;
import com.mountain.service.MobileCustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

/**
 * @description: test methods
 * @author: mountainINblack
 * @date: 2021/10/30 19:19
 */
@SpringBootTest
public class testMobileUsers {
    @Test
    public void testInsertMobileUsers(){
        MobileCustomer mobileCustomer = new MobileCustomer();
        mobileCustomer.setMobileId("black_mountain_1001");
        mobileCustomer.setCreateTime(new Date());
        mobileCustomer.setServiceStatus(Boolean.FALSE);
    }
}
