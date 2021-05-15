package com.mountain.controller;

import com.mountain.entity.InitPro;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/def")
public class SettingGetController {
//    从服务器端获取参数值
    @GetMapping("/getset")
    public InitPro trafficSetting(){
        InitPro setting = new InitPro(17,
                121.50717,31.027809,
                121.506737,31.028632,
                121.506976,31.036238);
        return setting;
    }
    @PostMapping("/postset")
    public Map<String,Object> PostSet(@RequestBody Map<String,Object> params){
        params.get("zoom");
        params.get("centerLat");
        params.get("centerLng");
        params.get("StartPlat");
        params.get("StartPlng");
        params.get("endPlat");
        params.get("endPlng");
        InitPro postSetting=new InitPro();
        return params;

    }
}
