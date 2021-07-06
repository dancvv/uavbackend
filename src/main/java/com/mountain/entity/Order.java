package com.mountain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private int id;
    private String passenger_id;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;
//    @JsonFormat(pattern = "HH:mm:ss")
    private String create_time;
//    @JsonFormat(pattern = "HH:mm:ss")
    private String check_ticket_time;
    private int up_stop_id;
    private int down_stop_id;
    private int ride_count;
    private String small_vehicle_id;
    private String car_id;

}
