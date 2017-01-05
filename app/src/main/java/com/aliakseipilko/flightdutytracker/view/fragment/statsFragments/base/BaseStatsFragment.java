package com.aliakseipilko.flightdutytracker.view.fragment.statsFragments.base;

import android.support.v4.app.Fragment;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;


public abstract class BaseStatsFragment extends Fragment {

    public abstract void setupDutyBarChart(BarData data, IAxisValueFormatter xAxisValueFormatter, IAxisValueFormatter yAxisValueFormatter);

    public abstract void setupFlightBarChart(BarData data, IAxisValueFormatter xAxisValueFormatter, IAxisValueFormatter yAxisValueFormatter);

    public abstract void showError(String message);

}
