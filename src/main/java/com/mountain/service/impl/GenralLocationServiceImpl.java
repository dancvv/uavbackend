package com.mountain.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mountain.Mapper.generalMapper;
import com.mountain.entity.GeneralLocation;
import com.mountain.service.GeneralLocationService;
import org.springframework.stereotype.Service;

@Service
public class GenralLocationServiceImpl extends ServiceImpl<generalMapper, GeneralLocation> implements GeneralLocationService {
}
