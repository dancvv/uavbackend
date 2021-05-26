package com.mountain.DAO;

import com.mountain.entity.Bus;
import com.mountain.entity.Location;
import com.mountain.entity.Stations;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Repository
public class BusDAO {
    private static Map<Integer, Bus> busSet;
    static {
        busSet=new HashMap<Integer,Bus>();
        busSet.put(2201,new Bus(null,2201,new Location(10717,131.027809),11,15,21,21,new Stations(10,"1无名站",new Location(121.50717,31.027809)),new Stations(21,"1秋明站",new Location(11.50717,131.027809))));
        busSet.put(2202,new Bus(null,2202,new Location(20717,231.027809),12,25,22,22,new Stations(20,"2无名站",new Location(221.50717,32.027809)),new Stations(22,"2秋明站",new Location(21.50717,231.027809))));
        busSet.put(2203,new Bus(null,2203,new Location(30717,331.027809),13,35,23,23,new Stations(30,"3无名站",new Location(321.50717,33.027809)),new Stations(23,"3秋明站",new Location(31.50717,331.027809))));
        busSet.put(2204,new Bus(null,2204,new Location(40717,431.027809),14,45,24,24,new Stations(40,"4无名站",new Location(421.50717,34.027809)),new Stations(24,"4秋明站",new Location(41.50717,431.027809))));
        busSet.put(2205,new Bus(null,2205,new Location(50717,531.027809),15,55,25,25,new Stations(50,"5无名站",new Location(521.50717,35.027809)),new Stations(25,"5秋明站",new Location(51.50717,531.027809))));
    }

//    查询公交ID，通过ID返回信息
    public Bus getBusbyId(Integer id){
        return busSet.getOrDefault(id, null);
    }
    public Collection<Bus> getAll(){
        return busSet.values();
    }
}
