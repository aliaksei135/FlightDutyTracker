package com.aliakseipilko.flightdutytracker.utils;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.HashMap;
import java.util.Map;


public class DayAxisValueFormatter implements IAxisValueFormatter {

    final int startCount;
    final int maxDays;

    Map<Float, Integer> labels = new HashMap<>();
    int count;
    float previousValue = -1;
    String[] daysOfMonth = {"1st", "2nd", "3rd", "4th", "5th", "6th", "7th", "8th", "9th", "10th", "11th", "12th", "13th", "14th", "15th", "16th", "17th", "18th", "19th", "20th", "21st", "22nd", "23rd", "24th", "25th", "26th", "27th", "28th", "29th", "30th", "31th"};

    public DayAxisValueFormatter(int startCount, int maxDays) {
        this.maxDays = maxDays;
        this.startCount = startCount;
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
        if (count > daysOfMonth.length - 1) {
            count = 0;
        }
        //Ensure month wraps
        if (count > maxDays - 1) {
            count = 0;
        }
        if (previousValue != -1) {
            if (previousValue + 1f != value) {
                count += (value - previousValue - 1f);
            }
        }
        previousValue = value;
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
