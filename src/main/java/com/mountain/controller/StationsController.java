package com.mountain.controller;

import com.mountain.entity.Location;
import com.mountain.entity.StationInfo;
import com.mountain.entity.Stations;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@Api(tags = {"站点信息"})
public class StationsController {
    @GetMapping("/station")
    public StationInfo GetStation(){
        StationInfo getStation=new StationInfo();
        getStation.setStation(new Stations(10,"无名站",new Location("121.50717","31.027809")));
        return getStation;
    }
    @PostMapping("/station")
    public StationInfo PostStation(@RequestBody StationInfo postStationInfo){
        return postStationInfo;
    }
}
