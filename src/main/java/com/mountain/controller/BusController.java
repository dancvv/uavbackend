package com.mountain.controller;

import com.mountain.DAO.BusDAO;
import com.mountain.DAO.RouteDAO;
import com.mountain.entity.Bus;
import com.mountain.entity.Location;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@Api(tags={"公交车信息"})
public class BusController {
    @Autowired
    BusDAO busDAO;
//    @Autowired
//    RouteDAO routeDAO;
    BusDAO bs = new BusDAO();
    @GetMapping("/getbus")
    public Bus GetBus(){
        Bus setBus=new Bus();
        return setBus;
    }
    @PostMapping("/postbus")
    public Bus PostBus(@RequestBody Bus setBus){
        return setBus;
    }
    @GetMapping("/businfo")
    public Collection<Bus> busInfo(){
        Collection<Bus> busInform = busDAO.getAll();
        final String UI= "FEFE";
        return busInform;
    }
}
