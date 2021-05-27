package com.mountain.DAO;

import com.mountain.entity.BusCompany;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
@Repository
public class BusCompanyDAO {
    private static BusCompany busCompany;
    static {
        busCompany=new BusCompany();
        busCompany.setAvailableBus(14);
        busCompany.setExistingBus(12);
        busCompany.setRunningBus(2);
        busCompany.setAverageRoute(49);
        busCompany.setAveRoutePassengerLength(23);
        busCompany.setTotalRoute(30);
    }
    public BusCompany getBusCompany(){
        System.out.println("test string");
        System.out.println(busCompany);
        return busCompany;
    }
}
