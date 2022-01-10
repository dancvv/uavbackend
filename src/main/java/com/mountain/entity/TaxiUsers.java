package com.mountain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "TaxiUsers")
public class TaxiUsers {
    @Id
    private String id;
    private String UserId;
    private int RiggerEvent;
    private int RunStatus;
    private String GPSTime;
    private Double Longitude;
    private Double Latitude;
    private int GPSSpeed;
    private int GPSPosition;
    private int GPSStatus;
}
