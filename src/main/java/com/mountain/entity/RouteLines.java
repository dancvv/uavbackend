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
    private Collection<T> routeSet;

    ArrayList<Integer> routeLine = new ArrayList<>();
}
