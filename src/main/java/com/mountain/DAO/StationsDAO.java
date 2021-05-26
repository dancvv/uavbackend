package com.mountain.DAO;

import com.mountain.entity.Location;
import com.mountain.entity.Stations;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
@Repository
public class StationsDAO {
//    存储所有站点信息
    private static Map<Integer, Stations> stationsMap;
    static {
        stationsMap=new HashMap<>();
        stationsMap.put(6 ,new Stations(6 ,"6",new Location(121.500551,31.02238 )));
        stationsMap.put(7 ,new Stations(7 ,"7",new Location(121.513037,31.060053)));
        stationsMap.put(8 ,new Stations(8 ,"8",new Location(121.512183,31.062434)));
        stationsMap.put(21,new Stations(21,"21",new Location(121.5075  ,31.034465)));
        stationsMap.put(22,new Stations(22,"22",new Location(121.507506,31.031005)));
        stationsMap.put(24,new Stations(24,"24",new Location(121.500189,31.030598)));
        stationsMap.put(25,new Stations(25,"25",new Location(121.499055,31.031862)));
        stationsMap.put(26,new Stations(26,"26",new Location(121.501475,31.034868)));
        stationsMap.put(27,new Stations(27,"27",new Location(121.503055,31.035358)));
        stationsMap.put(28,new Stations(28,"28",new Location(121.503314,31.030625)));
        stationsMap.put(31,new Stations(31,"31",new Location( 121.50793,31.03623 )));
        stationsMap.put(32,new Stations(32,"32",new Location(121.50789 ,31.038624)));
        stationsMap.put(33,new Stations(33,"33",new Location(121.510706,31.038639)));
        stationsMap.put(34,new Stations(34,"34",new Location(121.510715,31.03611 )));
        stationsMap.put(35,new Stations(35,"35",new Location(121.505514,31.0386  )));
        stationsMap.put(36,new Stations(36,"36",new Location(121.505554,31.036113)));
        stationsMap.put(37,new Stations(37,"37",new Location(121.514147,31.035587)));
        stationsMap.put(38,new Stations(38,"38",new Location(121.514142,31.038631)));

    }
    public Collection<Stations> getStation(){
//        获取所有站点的信息
        return stationsMap.values();
    }
    public Stations getStationById(Integer id){
//        通过ID查询各个站点信息
        return stationsMap.get(id);
    }
}
