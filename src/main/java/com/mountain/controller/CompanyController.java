package com.mountain.controller;

import com.mountain.entity.BusCompany;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api("公交公司运行路线的初始数据")
public class CompanyController {
    @GetMapping("/bus")
    @ApiOperation("获得公交公司运行路线的初始数据")
    public BusCompany GetbusCompany(){
        BusCompany operating = new BusCompany();
        operating.setAvailableBus(10);
        operating.setExistingBus(5);
        operating.setAverageRoute(25.0);
        operating.setRunningBus(20);
        return operating;
    }
    @PostMapping("/bus")
    @ApiOperation("更新公交公司运行路线的初始数据")
    public BusCompany PostbusCompany(@RequestBody BusCompany busCompany){
        return busCompany;
    }
}
