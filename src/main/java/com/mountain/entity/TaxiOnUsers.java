package com.mountain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaxiOnUsers {
    private String ID;
    private int RiggerEvent;
    private int RunStatus;
    private String GPSTime;
    private Double Longitude;
    private Double Latitude;
    private int GPSSpeed;
    private int GPSPosition;
    private int GPSStatus;
}
