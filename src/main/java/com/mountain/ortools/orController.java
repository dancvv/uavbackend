package com.mountain.ortools;

import com.mountain.DAO.orToolsDAO;
import com.mountain.entity.Location;
import com.mountain.service.impl.LocationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//解决跨域
@CrossOrigin
@RestController
@RequestMapping("/compute")
public class orController {
    @Autowired
    private LocationServiceImpl locationService;
    @PostMapping("/depotData")
    public Map<String,Object>  getRoute(@RequestBody Collection<Location> list){
//        boolean save = locationService.save(list);
//        保存单条数据
//        boolean save = locationService.saveOrUpdate(list);
        Map<String,Object> status=new HashMap<>();
        boolean save = locationService.saveOrUpdateBatch(list);
        if (!save){
            status.put("status",404);
            status.put("msg","更新失败，检查输入");
        }else {
            status.put("status",200);
            status.put("msg","更新成功");
        }
        System.out.println(status);
        System.out.println(list);
        System.out.println(save);
        return status;
    }
    @GetMapping("/list")
    public List<Location> showList(){
        List<Location> locationList = locationService.list();
        List<Map<String, Object>> maps = locationService.listMaps();
        System.out.println(locationList);
        return locationList;
    }
    @GetMapping("/delete")
    public void delete(){
    }
    @GetMapping("/count")
    public long querry(){
        final long count = locationService.count();
        return count;
    }
//    距离计算
    @Autowired
    private orToolsDAO orToolsDAO;
    @GetMapping("/plan")
    public void routePlan(){
        final int vehicleNumber=4;
//        目前的问题是如何计算一个数组的矩阵值

    }

}
