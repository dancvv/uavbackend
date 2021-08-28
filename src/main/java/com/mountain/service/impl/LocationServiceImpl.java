package com.mountain.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mountain.Mapper.LocaMapper;
import com.mountain.entity.Location;
import com.mountain.service.LocationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
//不知道什么用的注解
//@Transactional
public class LocationServiceImpl extends ServiceImpl<LocaMapper, Location> implements LocationService {
}
