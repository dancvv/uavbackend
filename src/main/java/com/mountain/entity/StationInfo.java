package com.mountain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
//车站具体信息，等车人数，时间
public class StationInfo {
    private Date date;
    private Stations station;
//    等待人数
    private int waitPeople;
}
