package com.mountain.service;

import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GlobalCoordinates;

/**
 * @description:
 * @author: mountainINblack
 * @date: 2021/11/6 16:21
 */
public interface CommonUtilsService {
    double calculateGeoPointsDistance(GlobalCoordinates source, GlobalCoordinates target, Ellipsoid ellipsoid);
}
