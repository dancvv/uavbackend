package com.mountain.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mountain.entity.Location;

import jdk.vm.ci.meta.Local;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocalMapper extends BaseMapper<Location> {
    @Update("TRUNCATE table location")
    void deleteAllLocations();

    /**
     * 查找特定用户的id
     * @param mobileid
     * @return
     */
    @Select("SELECT name_id FROM location WHERE mobileid=#{mobileid}")
    int findIndexByName(String mobileid);

    // 查找
    @Select("select name_id from location where mobileid like 'user%'")
    int[] findUsers();

    @Select("select count(*) from location where mobileid like 'depot%'")
    int existDepot();

    @Select("select * from location where name_id = #{index}")
    Location findLocationByIndex(int index);

    @Select("select * from location")
    List<Location> findAllUserAndDepot();
}
