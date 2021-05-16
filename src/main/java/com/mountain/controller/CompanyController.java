package com.mountain.controller;

import com.mountain.entity.BusCompany;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags= {"公交公司初始化设定"})
public class CompanyController {
//    一个路径下不能同时存在两个获取路径参数的情况
    @GetMapping("/bus")
    @ApiOperation("获得公交公司运行路线的初始数据")
    public BusCompany GetbusCompany(){
        BusCompany operating = new BusCompany();
        operating.setAvailableBus(10);
        operating.setExistingBus(5);
        operating.setAverageRoute(25.0);
        operating.setRunningBus(20);
        operating.setPassengerInBus(10);
        return operating;
    }
    @PostMapping("/bus")
    @ApiOperation("更新公交公司运行路线的初始数据")
    public BusCompany PostbusCompany(@RequestBody BusCompany busCompany){
        return busCompany;
    }

//    @GetMapping("/bus/{id}")
//    @ApiOperation("获得公交公司运行路线的初始数据")
//    public BusCompany GtbusCompany(@PathVariable("id") Integer id){
//        BusCompany operating = new BusCompany();
//        operating.setAvailableBus(10);
//        operating.setExistingBus(5);
//        operating.setAverageRoute(25.0);
//        operating.setRunningBus(20);
//        operating.setPassengerInBus(id);
//        return operating;
//    }
}
