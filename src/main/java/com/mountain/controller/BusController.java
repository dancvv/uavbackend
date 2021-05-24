package com.mountain.controller;

import com.mountain.entity.Bus;
import com.mountain.entity.Location;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags={"公交车信息"})
public class BusController {
    @GetMapping("/getbus")
    public Bus GetBus(){
        Bus setBus=new Bus();
        return setBus;
    }
    @PostMapping("/postbus")
    public Bus PostBus(@RequestBody Bus setBus){
        return setBus;
    }
}
