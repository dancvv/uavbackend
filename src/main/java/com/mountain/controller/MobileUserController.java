package com.mountain.controller;

import com.mongodb.client.result.UpdateResult;
import com.mountain.entity.CustomerLocation;
import com.mountain.entity.MobileCustomer;
import com.mountain.service.impl.MobileCustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: mountainINblack
 * @date: 2021/10/30 19:09
 */
@RestController
@RequestMapping("/mobile")
public class MobileUserController {
    @Autowired
    private MobileCustomerServiceImpl mobileCustomerService;
    @PostMapping("/saveusers")
    public Map<String,Object> insertMobileUser(MobileCustomer mobileCustomer){
        mobileCustomer.setCreateTime(LocalDateTime.now());
        return mobileCustomerService.insertMobileUsers(mobileCustomer);
    }
    @PostMapping("/onelocation")
    public Map<String,Object> upsertUserLocation(CustomerLocation customerLocation){
        customerLocation.setServiceTime(LocalDateTime.now());
        return mobileCustomerService.saveCustomerLocation(customerLocation);
    }
    @GetMapping("/findbyid")
    public MobileCustomer findByUserId(String id){
        return mobileCustomerService.findByUersId(id);
    }
    @DeleteMapping("/deleteuser")
    public String deleteUserById(String id){
        return mobileCustomerService.deleteByUserId(id);
    }
//    保存多个用户
    @PostMapping("/savemany")
    public Map<String,Object> saveManyMobileUsers(@RequestBody List<MobileCustomer> mobileCustomers){
        return mobileCustomerService.insertManyMobileUsers(mobileCustomers);
    }
//    保存多个用户的位置
    @PostMapping("/manylocations")
    public Map<String, Object> saveManyUsersLocations(@RequestBody List<CustomerLocation> customerLocationList){
        return mobileCustomerService.updateManyUsersLocation(customerLocationList);
    }
//    查询位置是否出界
    @GetMapping("/realtimeflex")
    public List<MobileCustomer> calculateUsersLocation(){
        return mobileCustomerService.LocationCompare();
    }
//    查询所有用户的状态信息
    @GetMapping("/getstatus")
    public List<MobileCustomer> getStatus(Boolean status){
        return mobileCustomerService.findUserStatus(status);
    }
//    查询出用户的logic信息，并更新
    @GetMapping("/updateLocation")
    public Map<Object, Object> queryAndUpdateLocation(){
        return mobileCustomerService.queryAndUpdateLocation();
    }
//    根据文档id查询,并将所有用户的logic状态更新为废弃状态2
    @GetMapping("/setlogicsta")
    public UpdateResult updateLogicStaById(@RequestParam String userId){
        return mobileCustomerService.updateOneUsersLogicStatus(userId);
    }
}
