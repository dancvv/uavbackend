package com.mountain.Repository;

import com.mountain.entity.Location;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
@Repository
public class StationRepository {
    //    存储所有站点信息
    private static Map<Integer, Location> stationsRepo;
    static {
        stationsRepo=new HashMap<>();
        stationsRepo.put(6 ,new Location(121.500551,31.02238 ));
        stationsRepo.put(7 ,new Location(121.513037,31.060053));
        stationsRepo.put(8 ,new Location(121.512183,31.062434));
        stationsRepo.put(21,new Location(121.5075  ,31.034465));
        stationsRepo.put(22,new Location(121.507506,31.031005));
        stationsRepo.put(24,new Location(121.500189,31.030598));
        stationsRepo.put(25,new Location(121.499055,31.031862));
        stationsRepo.put(26,new Location(121.501475,31.034868));
        stationsRepo.put(27,new Location(121.503055,31.035358));
        stationsRepo.put(28,new Location(121.503314,31.030625));
        stationsRepo.put(31,new Location( 121.50793,31.03623 ));
        stationsRepo.put(32,new Location(121.50789 ,31.038624));
        stationsRepo.put(33,new Location(121.510706,31.038639));
        stationsRepo.put(34,new Location(121.510715,31.03611 ));
        stationsRepo.put(35,new Location(121.505514,31.0386  ));
        stationsRepo.put(36,new Location(121.505554,31.036113));
        stationsRepo.put(37,new Location(121.514147,31.035587));
        stationsRepo.put(38,new Location(121.514142,31.038631));
    }
    public Collection<Location> getStation(){
//        获取所有站点的信息
        return stationsRepo.values();
    }
    public Location getStationById(Integer id){
//        通过ID查询各个站点信息
        return stationsRepo.get(id);
    }
}
