package com.aliakseipilko.flightdutytracker.utils;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.HashMap;
import java.util.Map;


public class MonthAxisValueFormatter implements IAxisValueFormatter {

    Map<Float, Integer> labels = new HashMap<>();

    String[] monthsOfYear = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    int count;

    public MonthAxisValueFormatter(int startCount) {
        count = startCount;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        //Check if value is decimal
        if (value % 1 != 0) {
            return "";
        }
        //Check for wrapping around
        //Ensure array stays in bounds
        if (count > monthsOfYear.length - 1) {
            count = 0;
        }

        if (labels.containsKey(value)) {
            count++;
            return monthsOfYear[labels.get(value)];
        } else {
            labels.put(value, count);
            count++;
            return monthsOfYear[labels.get(value)];
        }
    }
}
