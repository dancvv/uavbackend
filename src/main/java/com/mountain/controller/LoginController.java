package com.mountain.controller;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Api(tags = {"登录界面"})
public class LoginController {
    @RequestMapping("/login")
    public String Login(){
        return "LoginPage";
    }
}
