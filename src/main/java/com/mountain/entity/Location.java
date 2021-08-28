package com.mountain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Location {
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private Long id;
    private Double lat;
    private Double lng;
}
