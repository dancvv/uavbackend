package com.mountain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouteLines<T> {
    Collection<T> routeSet = new ArrayList<>();
    ArrayList<Integer> routeLine = new ArrayList<>();
}
