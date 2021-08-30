package com.mountain.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mountain.entity.GeneralLocation;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface generalMapper extends BaseMapper<GeneralLocation> {
    @Update("TRUNCATE table location")
    void deleteLocation();
}
