package com.mountain.controller;

import com.mountain.DAO.BusCompanyDAO;
import com.mountain.entity.BusCompany;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags= {"公交公司初始化设定"})
public class BusCompanyController {
    @Autowired
    BusCompanyDAO busCompanyDAO;

//    一个路径下不能同时存在两个获取路径参数的情况
    @GetMapping("/busu")
    @ApiOperation("获得公交公司运行路线的初始数据")
    public BusCompany GetbusCompany(){
        System.out.println("tes-t------------------");
        System.out.println(busCompanyDAO.getBusCompany());
        return busCompanyDAO.getBusCompany();
    }

    @PostMapping("/bus")
    @ApiOperation("更新公交公司运行路线的初始数据")
    public BusCompany PostbusCompany(@RequestBody BusCompany busCompany){
        return busCompany;
    }
}
