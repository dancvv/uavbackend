package com.mountain.controller;

import com.mountain.entity.InitPro;
import com.mountain.entity.Location;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api(tags= {"地图初始化设置"})
@RestController
@RequestMapping("/def")

public class SettingGetController {
//    从服务器端获取参数值
    @GetMapping("/init")
    @ApiOperation("获取初始化参数")
    public InitPro trafficSetting(){
//        初始化参数设置
        InitPro setting = new InitPro();
        setting.setZoom(17);
        setting.setCenterPoint(new Location("121.50717","31.027809"));
        setting.setStartPoint(new Location("121.506737","31.028632"));
        setting.setEndPoint(new Location("121.506976","31.036238"));
        setting.setWaitPeople(10);
        return setting;
    }
    @PostMapping("/init")
    @ApiOperation("使用自定义参数")
    public InitPro PostMethod(@RequestBody InitPro initPro){
//        上传自定义参数设置
        return initPro;
    }
    @Deprecated
    @PostMapping("/postset")
    public Map<String,Object> PostSet(@RequestBody Map<String,Object> params){
        params.get("zoom");
        params.get("centerLat");
        params.get("centerLng");
        params.get("StartPlat");
        params.get("StartPlng");
        params.get("endPlat");
        params.get("endPlng");
        params.get("waitPeople");
        InitPro postSetting=new InitPro();
        return params;
    }

}
