package com.aliakseipilko.flightdutytracker.view.fragment.statsFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aliakseipilko.flightdutytracker.R;
import com.aliakseipilko.flightdutytracker.presenter.impl.StatsPresenter;
import com.aliakseipilko.flightdutytracker.view.adapter.BaseChartAdapter;
import com.aliakseipilko.flightdutytracker.view.adapter.DayBarChartAdapter;
import com.aliakseipilko.flightdutytracker.view.fragment.StatsFragment;
import com.aliakseipilko.flightdutytracker.view.fragment.statsFragments.base.BaseStatsFragment;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import org.joda.time.DateTime;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TwentyEightDaysStatsFragment extends BaseStatsFragment {

    @BindView(R.id.twenty_eight_day_duty_barstat)
    BarChart dutyBarChart;
    @BindView(R.id.twenty_eight_day_flight_barstat)
    BarChart flightBarChart;

    private Unbinder unbinder;

    private StatsPresenter presenter;

    private BaseChartAdapter chartAdapter;

    public TwentyEightDaysStatsFragment() {
        // Required empty public constructor
    }


    public static TwentyEightDaysStatsFragment newInstance() {

        return new TwentyEightDaysStatsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_twenty_eight_days_stats, container, false);
        unbinder = ButterKnife.bind(this, view);

        chartAdapter = new DayBarChartAdapter(this);

        presenter = new StatsPresenter(this, chartAdapter);
        presenter.subscribeAllCallbacks();
        presenter.getMultipleFlightsByDateRange(DateTime.now().minusDays(28).toDate(), new Date());

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setupDutyBarChart(BarData data, IAxisValueFormatter xAxisValueFormatter, IAxisValueFormatter yAxisValueFormatter) {
        dutyBarChart.setFitBars(true);
        dutyBarChart.setData(data);
        dutyBarChart.getXAxis().setValueFormatter(xAxisValueFormatter);
        dutyBarChart.getAxisLeft().setEnabled(true);
        dutyBarChart.getAxisLeft().setValueFormatter(yAxisValueFormatter);
        dutyBarChart.getAxisRight().setEnabled(false);
        dutyBarChart.setDescription(null);
        dutyBarChart.setHighlightFullBarEnabled(false);
        dutyBarChart.setDoubleTapToZoomEnabled(false);
        dutyBarChart.setPinchZoom(false);
        dutyBarChart.invalidate();
    }

    @Override
    public void setupFlightBarChart(BarData data, IAxisValueFormatter xAxisValueFormatter, IAxisValueFormatter yAxisValueFormatter) {
        flightBarChart.setFitBars(true);
        flightBarChart.setData(data);
        flightBarChart.getXAxis().setValueFormatter(xAxisValueFormatter);
        flightBarChart.getAxisLeft().setEnabled(true);
        flightBarChart.getAxisLeft().setValueFormatter(yAxisValueFormatter);
        flightBarChart.getAxisRight().setEnabled(false);
        flightBarChart.setDescription(null);
        flightBarChart.setDoubleTapToZoomEnabled(false);
        flightBarChart.setPinchZoom(false);
        flightBarChart.invalidate();
    }

    @Override
    public void showError(String message) {
        ((StatsFragment) getParentFragment()).showError(message);
    }
}
