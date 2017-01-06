package com.aliakseipilko.flightdutytracker.utils;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.HashMap;
import java.util.Map;


public class DayAxisValueFormatter implements IAxisValueFormatter {

    final int startCount;
    final int maxDays;
    boolean isMonthWrapping;
    Map<Float, Integer> labels = new HashMap<>();
    int count;
    float previousValue = -1;
    String[] daysOfMonth = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};

    public DayAxisValueFormatter(boolean isMonthWrapping, int startCount, int maxDays) {
        this.isMonthWrapping = isMonthWrapping;
        this.maxDays = maxDays;
        this.startCount = startCount;
        count = startCount;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        if (previousValue != -1) {
            if (previousValue + 1f != value) {
                count += (value - previousValue - 1f);
            }
        }
        previousValue = value;
        if (count > daysOfMonth.length - 1) {
            count = 0;
        }
        if (count > maxDays - 1) {
            count = 0;
        }

        if (labels.containsKey(value)) {
            count++;
            return daysOfMonth[labels.get(value)];
        } else {
            labels.put(value, count);
            count++;
            return daysOfMonth[labels.get(value)];
        }
    }

}
