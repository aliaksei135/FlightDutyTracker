/*
 * Copyright (C) 2017 Aliaksei Pilko
 *
 * Licensed under the Apache License, Version 2.0 (the “License”); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an “AS IS” BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package com.aliakseipilko.flightdutytracker.view.fragment.statsFragments.base;

import android.support.v4.app.Fragment;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;


public abstract class BaseStatsFragment extends Fragment {

    public abstract void setupDutyBarChart(BarData data, IAxisValueFormatter xAxisValueFormatter, IAxisValueFormatter yAxisValueFormatter);

    public abstract void setupFlightBarChart(BarData data, IAxisValueFormatter xAxisValueFormatter, IAxisValueFormatter yAxisValueFormatter);

    public abstract void showError(String message);

}
