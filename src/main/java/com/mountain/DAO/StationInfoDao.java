package com.mountain.DAO;

import com.mountain.entity.StationInfo;
import com.mountain.entity.Stations;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class StationInfoDao {
    private static Map<Integer, StationInfo> stationInfoMap;
    static {
        stationInfoMap=new HashMap<>();
    }
}
