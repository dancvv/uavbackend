package com.mountain.controller;

import com.mountain.Repository.StationRepository;
import com.mountain.entity.Location;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@Api(tags = {"站点信息"})
public class StationsController {
    @Autowired
    StationRepository stationRepo;

    @GetMapping("/station")
    public Collection<Location> GetStation() {
//        获取所有车站信息
        return stationRepo.getStation();
    }

    @GetMapping("/station/{id}")
    public Location GetStationID(@RequestParam("id") Integer id) {
//        通过ID获取车站信息
        return stationRepo.getStationById(id);
    }
}
