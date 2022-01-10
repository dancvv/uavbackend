package com.mountain;

import com.mountain.entity.TaxiUsers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class testTaxiUser {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void testGetAll() {

        Query query = new Query(Criteria.where("GPSTime").is("20121101002419"));
        TaxiUsers one = mongoTemplate.findOne(query, TaxiUsers.class);
        System.out.println("-----------------");
        System.out.println(one);

    }

}