package com.mountain.controller;

import com.mountain.DAO.orToolsDAO;
import com.mountain.Mapper.LocalMapper;
import com.mountain.entity.Location;
import com.mountain.service.impl.MobileCustomerServiceImpl;
import com.mountain.service.impl.PositionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
    private LocalMapper localMapper;
    @Autowired
    private MobileCustomerServiceImpl mobileCustomerService;

    @Autowired
    private orToolsDAO ordao;
    @PostMapping("/uploadData")
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
    @GetMapping("/deleteALL")
    public Map<String,Object> deleteAll(){
        Map<String,Object> infoMap=new HashMap<>();
//        删除主表
        localMapper.deleteAllLocations();
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
    public Map<String,Object> routeParamAndPlan(@RequestParam Integer vehicleNumber ,@RequestParam Integer depot){
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
//            返回当前当前需要进行计算的用户
            final Double[][] locationMatrix = ordao.locationMatrix(locationList);
//            计算所有用户的距离矩阵
            final Long[][] distanceMatrix = ordao.computeDistance(locationMatrix);
//        根据计算出来的矩阵开始调用后端计算
//        路线长度，车辆数量，车站数量
            Map<Object, ArrayList<Integer>> routeList =positionService.planCompute( distanceMatrix, vehicleNumber, depot);
//            System.out.println(routeList);
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
//    动态规划路线算法
    @GetMapping("passengermoving")
    public Map<Object, ArrayList<Integer>> uploadMovingPassenger(int[] starts,int vehicleNum){

        final int[] stop = new int[starts.length];
        List<Location> locationList = positionService.list();
//        距离矩阵计算
        final Double[][] locationMatrix = ordao.locationMatrix(locationList);
        final Long[][] distanceMatrix = ordao.computeDistance(locationMatrix);
//        根据计算出来的矩阵开始调用后端计算
//        路线长度，车辆数量，车站数量
        Map<Object, ArrayList<Integer>> routeList =positionService.movePassengerPlan(distanceMatrix,vehicleNum,starts,stop);
        return routeList;
    }
//    动态更新位置
    @PostMapping("/dynamicLocation")
    public Map<Object, ArrayList<Integer>> dynamicRoutes(@RequestBody Map<String,Location> uavLocation,@RequestBody Map<String,Location> depotLocation,@RequestParam String uuid){
        Map<Object, Object> objectObjectMap = mobileCustomerService.queryAndUpdateLocation(uuid);
        Map<String, GeoJsonPoint> mobileLocation = (Map<String, GeoJsonPoint>) objectObjectMap.get("locations");
        // 保存动态位置到数据库
        Boolean status = positionService.dynamicLocationSave(mobileLocation,uavLocation,depotLocation);
        if (status){
            Map<Object, ArrayList<Integer>> objectArrayListMap = positionService.dynamicRoutes(uavLocation);
            return objectArrayListMap;
        }else {
            System.out.println("something wrong happened");
            return null;
        }
    }
//    保存所有用户的位置
    @PostMapping("/saveAllLocation")
    public String saveDynamicLocation(@RequestParam String uuid){
//        确定唯一的uuid
        Map<Object, Object> objectObjectMap = mobileCustomerService.queryAndUpdateLocation(uuid);
        Map<String, GeoJsonPoint> locations = (Map<String, GeoJsonPoint>) objectObjectMap.get("locations");
        positionService.saveOneListUsers(locations);
        return "success";
    }

    @GetMapping("/findbynm")
    public Integer findbyUserName(@RequestParam String name){
        return positionService.findIndexByName(name);
    }

    @PostMapping("/datetest")
    public void dateTest(@RequestBody LocalDateTime ll){
        System.out.println(ll);
    }

    @GetMapping("/findusers")
    public int[] findUn
}
