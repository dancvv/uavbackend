package com.mountain.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mountain.entity.Location;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalMapper extends BaseMapper<Location> {
    @Update("TRUNCATE table location")
    void deleteLocation();
}
