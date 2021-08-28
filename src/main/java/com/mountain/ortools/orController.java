package com.mountain.ortools;

import com.mountain.entity.Location;
import com.mountain.service.impl.LocationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/compute")
public class orController {
    @Autowired
    private LocationServiceImpl locationService;
    @PostMapping("/depotData")
    public void getRoute(@RequestBody Collection<Location> list){
//        boolean save = locationService.save(list);
//        保存单条数据
//        boolean save = locationService.saveOrUpdate(list);
        boolean save = locationService.saveOrUpdateBatch(list);
        System.out.println(list);
        System.out.println(save);
//        boolean b = locationService.saveBatch(list);
//        System.out.println(b);
    }
    @GetMapping("/list")
    public List<Location> showList(){
        List<Location> list = locationService.list();
        List<Map<String, Object>> maps = locationService.listMaps();
        System.out.println(maps);
        return list;
    }
    @GetMapping("/delete")
    public void delete(){
    }

}
