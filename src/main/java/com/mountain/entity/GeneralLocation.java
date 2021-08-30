package com.mountain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeneralLocation {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Double lng;
    private Double lat;
}
