package com.mountain.controller;

import com.mountain.DAO.MobileCustomerRepository;
import com.mountain.entity.CustomerLocation;
import com.mountain.entity.MobileCustomer;
import com.mountain.service.impl.MobileCustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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
        mobileCustomer.setCreateTime(new Date());
        return mobileCustomerService.insertMobileUsers(mobileCustomer);
    }
    @PostMapping("/onelocation")
    public Map<String,Object> upsertUserLocation(CustomerLocation customerLocation){
        customerLocation.setServiceTime(new Date());
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
//    查询用户状态
    @GetMapping("/status")
    public List<MobileCustomer> getStatus(Boolean status){
        return mobileCustomerService.findUserStatus(status);
    }
//    查询内嵌文档
    @GetMapping("/mobileid")
    public List<Map<Object, Boolean>> getMobilId(){
        mobileCustomerService.findEmbedDocument();
        return null;
    }
}
