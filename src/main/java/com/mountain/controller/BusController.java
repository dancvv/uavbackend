package com.mountain.controller;

import com.mountain.entity.Bus;
import com.mountain.entity.Location;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BusController {
    @GetMapping("/bus")
    public Bus GetBus(){
        Bus setBus=new Bus();
        return setBus;
    }
    @PostMapping("/bus")
    public Bus PostBus(@RequestBody Bus setBus){
        return setBus;
    }
}
