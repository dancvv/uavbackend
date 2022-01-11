package com.mountain.controller;

import java.util.List;

import com.mountain.entity.TaxiUsers;
import com.mountain.service.impl.TaxiUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/taxiuser")
public class TaxiUserController {

    @Autowired
    private TaxiUserServiceImpl taxiUserService;

    @GetMapping("/getLocation")
    public List<TaxiUsers> getLocation() {
        List<TaxiUsers> allLocations = taxiUserService.getAllLocations();
        // System.out.println(allLocations);
        return allLocations;
    }
}
