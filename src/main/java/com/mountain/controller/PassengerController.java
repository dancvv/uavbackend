package com.mountain.controller;

import com.mountain.entity.PassengerQOS;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags={"乘客QOS设定"})
public class PassengerController {
    @GetMapping("/passenger")
    @ApiOperation("获取乘客QOS要求，单位为分钟")
    public PassengerQOS passengerQOS(){
        PassengerQOS QOS = new PassengerQOS();
        QOS.setAveWaitTime(10.0);
        QOS.setMaxWaitTime(30.0);
        QOS.setAvePassengerRouteLength(30.2);
        return QOS;
    }
    @PostMapping("/passenger")
    @ApiOperation("更新乘客QOS要求，单位为分钟")
    public PassengerQOS passengerQOS(@RequestBody PassengerQOS passengerQOS){
        return passengerQOS;
    }
}
