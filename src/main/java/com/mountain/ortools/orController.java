package com.mountain.ortools;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mountain.DAO.orToolsDAO;
import com.mountain.Mapper.LocaMapper;
import com.mountain.entity.Location;
import com.mountain.service.LocationService;
import com.mountain.service.impl.LocationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
    @Autowired
    private orToolsDAO orDAO;
    @GetMapping("/list")
    public List<Location> showList(){

        List<Location> locationList = locationService.list();
        List<Map<String, Object>> maps = locationService.listMaps();


        return locationList;
    }
    @Autowired
    private LocaMapper locaMapper;
    @GetMapping("/delete")
    public Map<String,Object> delete(){
        Map<String,Object> infoMap=new HashMap<>();
        locaMapper.deleteLocation();
        infoMap.put("msg","调用成功");
        infoMap.put("status",200);
        System.out.println("deede");
        return infoMap;
    }

    @GetMapping("/count")
    public long querry(){
        final long count = locationService.count();
        return count;
    }
//    距离计算
    @GetMapping("/plan")
    public Map<String,Object> routePlan(){
        Map<String,Object> infoMap=new HashMap<>();
//        目前的问题是如何计算一个数组的矩阵值
        List<Location> locationList = locationService.list();
        List<Map<String, Object>> maps = locationService.listMaps();
        if (locationList.size()<=2){
            infoMap.put("status","服务器中资源不足，添加数据");
            infoMap.put("msg",404);
        }else {
            infoMap.put("status","调用成功");
            infoMap.put("msg",200);
//            调用距离计算模块
            final Double[][] distanceMatrix = locationService.distanceCompute(locationList);
//        根据计算出来的矩阵开始调用后端计算
//        路线长度，车辆数量，车站数量
            Map<Integer, ArrayList<Integer>> routeList = locationService.mainCompute(distanceMatrix, 4, 2);
            infoMap.put("info",routeList);
        }
        return infoMap;
    }
    @GetMapping("/routeList")
    public void getRouteList(){
    }
}
