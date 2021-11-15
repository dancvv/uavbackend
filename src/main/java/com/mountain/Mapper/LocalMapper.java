package com.mountain.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mountain.entity.Location;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalMapper extends BaseMapper<Location> {
    @Update("TRUNCATE table location")
    void deleteAllLocations();

    @Query("SELECT name_id FROM location WHERE mobileid=?1")
    int findIndexByName(String mobileid);
}
