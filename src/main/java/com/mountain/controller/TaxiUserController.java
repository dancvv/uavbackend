package com.mountain.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/taxiuser")
public class TaxiUserController {
    @GetMapping("getLocation")
    public void getLocation() {
        
    }
}
