package com.mountain.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mountain.entity.Location;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalMapper extends BaseMapper<Location> {
    @Update("TRUNCATE table location")
    void deleteAllLocations();

    @Select("SELECT name_id FROM location WHERE mobileid=#{mobileid}")
    public int findIndexByName(String mobileid);

    // 查找
    @Select("select name_id from location where mobileid like #{}")
    public int[] findUsers()
}
