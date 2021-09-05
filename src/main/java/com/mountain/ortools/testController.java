package com.mountain.ortools;

import com.mountain.service.impl.VrpGlobalSpan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("plan")
public class testController {
    @Autowired
    private VrpGlobalSpan vrpGlobalSpan;

    @GetMapping("/map")
    public void plan(){
    }


}
