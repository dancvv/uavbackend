package com.mountain.controller;

import com.mountain.DAO.BusDAO;
import com.mountain.DAO.RouteDAO;
import com.mountain.entity.Bus;
import com.mountain.entity.Location;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@Api(tags={"公交车信息"})
public class BusController {
    @Autowired
    BusDAO busDAO;
//    @Autowired
//    RouteDAO routeDAO;
    BusDAO bs = new BusDAO();
//    通过ID查找车次
    @GetMapping("/getbus/{id}")
    public Bus GetBus(@PathVariable("id") Integer id){
        Bus busInfo=busDAO.getBusbyId(id);
//        return busDAO.getBusbyId(id);
        return busInfo;
//        return busInfo;
    }
    @PostMapping("/postbus")
    public Bus PostBus(@RequestBody Bus setBus){
        return setBus;
    }
    @GetMapping("/businfo")
//    返回公交车的所有信息
    public Collection<Bus> busInfo(){
        return busDAO.getAll();
    }
}
