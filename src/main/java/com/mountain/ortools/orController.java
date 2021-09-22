package com.mountain.ortools;

import com.mountain.DAO.orToolsDAO;
import com.mountain.Mapper.LocaMapper;
import com.mountain.entity.Location;
import com.mountain.service.impl.PositionServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

//解决跨域
@CrossOrigin
@RestController
@RequestMapping("/compute")
public class orController {
    @Autowired
    private PositionServiceImpl positionService;
//    标准化地理数据服务
    @Autowired
    private LocaMapper locaMapper;

    @Autowired
    private orToolsDAO ordao;
    @PostMapping("/depotData")
    public Map<String,Object>  postRoute(@RequestBody Collection<Location> list){
//        boolean save = locationService.save(list);
//        保存单条数据
//        boolean save = locationService.saveOrUpdate(list);
        Map<String,Object> status=new HashMap<>();
        boolean save = positionService.saveBatch(list);
//        boolean save = locationService.saveOrUpdateBatch(list);
        if (!save){
            status.put("status",404);
            status.put("msg","上传失败，检查输入");
        }else {
            status.put("status",200);
            status.put("msg","上传成功");
        }
        System.out.println(status);
        System.out.println(list);
        System.out.println(save);
        return status;
    }
    @Autowired
    @GetMapping("/list")
    public Map<String,Object> showList(){
        Map<String,Object> infoMap = new HashMap<>();
        List<Location> locationList = positionService.list();
        infoMap.put("msg","获取所有坐标点信息");
        infoMap.put("status",200);
        infoMap.put("data",locationList);
        return infoMap;
    }
//    删除所有表数据
    @GetMapping("/delete")
    public Map<String,Object> delete(){
        Map<String,Object> infoMap=new HashMap<>();
//        删除主表
        locaMapper.deleteLocation();
        infoMap.put("msg","删除后台数据成功");
        infoMap.put("status",200);
        return infoMap;
    }
//查询总共有多少条数据
    @GetMapping("/count")
    public Long querry(){
        return positionService.count();
    }
//    规划路线
    @PostMapping("/plan")
    public Map<String,Object> routePlan(@RequestParam Integer vehicleNumber ,@RequestParam Integer depot){
        Map<String,Object> infoMap=new HashMap<>();
//        距离矩阵
        List<Location> locationList = positionService.list();
        if (locationList.size()<=2){
            infoMap.put("msg","服务器中资源不足，添加数据");
            infoMap.put("status",404);
        }else {
            infoMap.put("msg","规划求解成功");
            infoMap.put("status",200);
//            调用距离计算模块
            final Double[][] locationMatrix = ordao.locationMatrix(locationList);
            final Long[][] distanceMatrix = ordao.computeDistance(locationMatrix);
//        根据计算出来的矩阵开始调用后端计算
//        路线长度，车辆数量，车站数量
            Map<Object, ArrayList<Integer>> routeList =positionService.planCompute( distanceMatrix, vehicleNumber, depot);
            System.out.println(routeList);
            infoMap.put("info",routeList);
        }
        return infoMap;
    }
    @GetMapping("/getLocationByID")
    public Map<String,Object> locationById(@RequestParam String locationId){
        Map<String,Object> infoMap = new HashMap<>();
        Location tempLocation = new Location();
        try{
            tempLocation = positionService.getById(locationId);
            infoMap.put("msg","根据id查询坐标成功");
            infoMap.put("status",200);
            infoMap.put("location",tempLocation);
        } catch (Exception e) {
            infoMap.put("status",404);
            infoMap.put("msg","查询失败");
            e.printStackTrace();
        }
        return infoMap;
    }
//    上传移动用户的当前位置
    @GetMapping("passengermoving")
    public Map<Object, ArrayList<Integer>> uploadMovingPassenger(){
        final int[] starts={12,5,6,9};
        final int[] stop={0,0,0,0};
        List<Location> locationList = positionService.list();
        final Double[][] locationMatrix = ordao.locationMatrix(locationList);
        final Long[][] distanceMatrix = ordao.computeDistance(locationMatrix);
//        根据计算出来的矩阵开始调用后端计算
//        路线长度，车辆数量，车站数量
        Map<Object, ArrayList<Integer>> routeList =positionService.movePassengerPlan(distanceMatrix,4,starts,stop);
        return routeList;
    }
}
