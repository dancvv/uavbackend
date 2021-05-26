package com.mountain.controller;

import com.mountain.DAO.StationsDAO;
import com.mountain.entity.Location;
import com.mountain.entity.StationInfo;
import com.mountain.entity.Stations;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Date;

@RestController
@Api(tags = {"站点信息"})
public class StationsController {
    @Autowired
    StationsDAO stationDao;
    @GetMapping("/station")
    public Collection<Stations> GetStation(){
//        获取各个车站信息
        return stationDao.getStation();
    }
    @GetMapping("/station/{id}")
    public Stations GetStationID(@RequestParam("id") Integer id){
        return stationDao.getStationById(id);
    }
    @PostMapping("/station")
    public StationInfo PostStation(@RequestBody StationInfo postStationInfo){
        return postStationInfo;
    }
}
