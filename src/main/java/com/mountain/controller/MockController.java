package com.mountain.controller;

import com.mountain.Repository.StationRepository;
import com.mountain.service.MockService;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class MockController {
    @Autowired
    MockService mockService;
    @Autowired
    StationRepository stationsRepo;
    @GetMapping("/mock")
    public List<Map<String,Object>> MockApi(){
        List<Map<String,Object>> list_new=new LinkedList<>();
        List<Map<String,Object>> list_map= mockService.queryList();
        for (Map<String, Object> map : list_map) {
            var hasMap=new HashMap<String,Object>();
            int station = (int) map.get("up_stop_id");
//            System.out.println(station);
            hasMap.put("station_id", station);
            hasMap.put("latlng", stationsRepo.getStationById(station));
            System.out.println(hasMap);
            list_new.add(hasMap);
        }

//        System.out.println(list_new);

        return list_new;
    }
}
