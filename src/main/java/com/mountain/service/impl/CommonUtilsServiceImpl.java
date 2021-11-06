package com.mountain.service.impl;

import com.mountain.service.CommonUtilsService;
import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GeodeticCurve;
import org.gavaghan.geodesy.GlobalCoordinates;
import org.springframework.stereotype.Service;

/**
 * @description: 公共服务service
 * @author: mountainINblack
 * @date: 2021/11/6 16:26
 */
@Service
public class CommonUtilsServiceImpl implements CommonUtilsService {
    @Override
    public double calculateGeoPointsDistance(GlobalCoordinates source, GlobalCoordinates target, Ellipsoid ellipsoid) {
        GeodeticCurve geodeticCurve = new GeodeticCalculator().calculateGeodeticCurve(ellipsoid, source, target);
        return geodeticCurve.getEllipsoidalDistance();
    }
}
