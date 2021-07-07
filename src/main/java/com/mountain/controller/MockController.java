package com.mountain.controller;

import com.mountain.DAO.MockDAO;
import com.mountain.Repository.StationRepository;
import com.mountain.service.MockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
@Api(tags = "mock")
@RestController
public class MockController {
    @Autowired
    MockService mockService;
    @Autowired
    StationRepository stationsRepo;
    @GetMapping("/mock")
    @ApiOperation(value = "2017-04-01")
    public List<Map<String,Object>> MockApi(){
        List<Map<String,Object>> list_new=new LinkedList<>();
        List<Map<String,Object>> list_map= mockService.queryList();
        for (Map<String, Object> map : list_map) {
            var hasMap=new HashMap<String,Object>();
            int station = (int) map.get("up_stop_id");
            hasMap.put("passenger_id",map.get("passenger_id"));
            hasMap.put("station_id", station);
            hasMap.put("latlng", stationsRepo.getStationById(station));
//            System.out.println(hasMap);
            list_new.add(hasMap);
        }

//        System.out.println(list_new);

        return list_new;
    }
    @GetMapping("/orderdata")
    @ApiOperation(value = "订单信息")
    public List<Map<String,Object>> getOrderData(){
        List<Map<String,Object>> list_order=new LinkedList<>();
        List<Map<String,Object>> list_map= mockService.queryList();
        for (Map<String, Object> map : list_map) {
            var hasMap=new HashMap<String,Object>();
            hasMap.put("create_date", map.get("date"));
            hasMap.put("create_time", map.get("create_time"));
            hasMap.put("up_stop_id", map.get("up_stop_id"));
            hasMap.put("down_stop_id", map.get("down_stop_id"));
            list_order.add(hasMap);
        }
        return list_order;
    }
    @GetMapping("/person")
    @ApiOperation(value = "站点等待人数信息")
    public List<Map<String,Object>> getPerson(){

        List<Map<String,Object>> list_people=new LinkedList<>();
        List<Map<String,Object>> list_map= mockService.queryOrder();
        for (Map<String, Object> map : list_map) {
            int station = (int) map.get("up_stop_id");
            var hasMap=new HashMap<String,Object>();
            hasMap.put("车站",station);
            hasMap.put("等待人数", map.get("result"));
            hasMap.put("position",stationsRepo.getStationById(station));
            list_people.add(hasMap);
        }
        return list_people;
    }
}
