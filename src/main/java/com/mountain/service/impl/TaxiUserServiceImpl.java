package com.mountain.service.impl;

import java.util.List;

import com.mountain.entity.TaxiUsers;
import com.mountain.service.TaxiUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class TaxiUserServiceImpl implements TaxiUserService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<TaxiUsers> getAllLocations() {
        // Double dle = new Double(20121101002419);
        // Query query = new Query(Criteria.where("Longitude").is(116.353096));
        List<TaxiUsers> all = mongoTemplate.findAll(TaxiUsers.class);
        System.out.println(all);
        return all;
    }

}