package com.mountain.controller;

import com.mongodb.client.result.UpdateResult;
import com.mountain.entity.CustomerLocation;
import com.mountain.entity.MobileCustomer;
import com.mountain.service.impl.MobileCustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: mountainINblack
 * @date: 2021/10/30 19:09
 */
@CrossOrigin
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
        System.out.println(mobileCustomers);
        System.out.println("___________");
        return mobileCustomerService.saveManyMobileUsers(mobileCustomers);
    }
//    保存多个用户的位置
    @PostMapping("/manylocations")
    public Map<String, Object> saveManyUsersLocations(@RequestBody List<CustomerLocation> customerLocationList){
        System.out.println(customerLocationList);
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
//    根据唯一的uuid，查询出所有用户的logic信息，并更新
    @PostMapping("/updateLocation")
    public Map<Object, Object> queryAndUpdateLocation(@RequestParam String uuid){
        return mobileCustomerService.queryAndUpdateLocation(uuid);
    }

    /**
     * 根据文档id查询,并将所有用户的logic状态更新为废弃状态2
     * @param userId 用户id
     * @param uuid 当前用户批次
     * @return 返回查询结果
     */
    @GetMapping("/setlogicsta")
    public UpdateResult updateLogicStaById(@RequestParam String userId,@RequestParam String uuid){
        return mobileCustomerService.updateOneUsersLogicStatus(userId,uuid);
    }

    /**
     * 删除所有用户
     * @return 返回删除结果
     */
    @DeleteMapping("/deleteall")
    public String deleteAllUsers(){
        return mobileCustomerService.deleteAllUsers();
    }

    /**
     * 查询出最近用户的UUID
     * @return 返回最新用户的uuid
     */
    @GetMapping("/findlastuuid")
    public Map<String, Object> findLastUUID(){
        Map<String, Object> newestUUID = mobileCustomerService.findNewestUUID();
        System.out.println(newestUUID);
        return newestUUID;
    }

    /**
     * 查询是否存在这一个用户批次
     * @param uuid 唯一用户
     * @return 信息
     */
    @PostMapping("/existuuid")
    public Map<String,Object> exitUUID(@RequestParam String uuid){
        return mobileCustomerService.exitUUID(uuid);
    }
}

