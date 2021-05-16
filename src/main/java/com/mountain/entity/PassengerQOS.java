package com.mountain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassengerQOS {
    private Double AveWaitTime;
    private Double MaxWaitTime;

}
