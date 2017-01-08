/*
 * Copyright (C) 2017 Aliaksei Pilko
 *
 * Licensed under the Apache License, Version 2.0 (the “License”); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an “AS IS” BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

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
