package com.mountain.Mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mountain.entity.ClusterResult;

import org.apache.ibatis.annotations.Select;

public interface ClusterMapper extends BaseMapper<ClusterResult>{

    /**
     * 查找批量用户
     * @return
     */
    @Select("select * from cluster_result")
    List<ClusterResult> findAll();
}
