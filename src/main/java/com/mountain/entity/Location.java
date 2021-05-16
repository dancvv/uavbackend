package com.mountain.entity;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Api("存储位置")
public class Location {
    public  String lat;
    private String lng;
}
