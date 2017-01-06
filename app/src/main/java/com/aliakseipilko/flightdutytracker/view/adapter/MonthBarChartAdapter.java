package com.aliakseipilko.flightdutytracker.view.adapter;


import com.aliakseipilko.flightdutytracker.realm.model.Flight;
import com.aliakseipilko.flightdutytracker.utils.MonthAxisValueFormatter;
import com.aliakseipilko.flightdutytracker.view.fragment.statsFragments.base.BaseStatsFragment;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import org.joda.time.DateTime;
import org.joda.time.Seconds;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmResults;

public class MonthBarChartAdapter extends BaseChartAdapter {

    RealmResults<Flight> flights;

    BaseStatsFragment view;

    public MonthBarChartAdapter(BaseStatsFragment view) {
        this.view = view;
    }

    @Override
    public void process(RealmResults<Flight> flights) {
        this.flights = flights;
        processDutyHours();
        processFlightHours();
    }

    private void processDutyHours() {
        List<BarEntry> barDutyEntries = new ArrayList<>();

        float xValueCount = 0f;

        long startMonth = new DateTime(flights.minDate("startDutyTime")).getMonthOfYear();

        long currentYear = 0;
        long currentMonth = 0;

        for (Flight flight : flights) {
            DateTime startDateTime = new DateTime(flight.getStartDutyTime());
            DateTime endDateTime = new DateTime(flight.getEndDutyTime());

            float decHoursDiff = (Seconds.secondsBetween(startDateTime, endDateTime).getSeconds() / 60f) / 60f;

            if (currentYear == 0 && currentMonth == 0) {
                currentYear = startDateTime.getYear();
                currentMonth = startDateTime.getMonthOfYear();

                //Add new entry using day of month as x value
                BarEntry newEntry = new BarEntry(xValueCount, decHoursDiff);
                barDutyEntries.add(newEntry);
                xValueCount++;
            } else {
                if (currentMonth + 1 < 13) {
                    //Add on any skipped months to xValueCount
                    if (currentMonth + 1 != startDateTime.getMonthOfYear()) {
                        xValueCount += (startDateTime.getMonthOfYear() - currentMonth) - 1;
                    }
                }
                //Check if the month is the same as the previous flight
                // All flights are provided in an ordered list by the repo
                if (currentMonth == startDateTime.getMonthOfYear()
                        && currentYear == startDateTime.getYear()) {
                    //Get last entry in list
                    BarEntry lastEntry = barDutyEntries.get(barDutyEntries.size() - 1);
                    //Add the additional hours in that day on, X value (the date) does not change
                    lastEntry = new BarEntry(lastEntry.getX(), lastEntry.getY() + decHoursDiff);
                    //Remove the last entry and add the modified entry instead of it
                    barDutyEntries.remove(barDutyEntries.size() - 1);
                    barDutyEntries.add(lastEntry);
                } else {
                    //Check if days of month wrap around
                    if (startDateTime.getYear() != currentYear) {

                    }

                    //New month
                    //Update these for the next iteration
                    currentYear = startDateTime.getYear();
                    currentMonth = startDateTime.getMonthOfYear();

                    //Add new entry using day of month as x value
                    BarEntry newEntry = new BarEntry(xValueCount, decHoursDiff);
                    barDutyEntries.add(newEntry);
                    xValueCount++;
                }
            }
        }

        IAxisValueFormatter xAxisValueFormatter = new MonthAxisValueFormatter(((int) startMonth - 1));
        IAxisValueFormatter yAxisValueFormatter = new DefaultAxisValueFormatter(2);
        BarDataSet dataSet = new BarDataSet(barDutyEntries, "Duty Hours");
        dataSet.setValueTextSize(16f);
        BarData barDutyData = new BarData(dataSet);
        barDutyData.setBarWidth(0.9f);
        barDutyData.setHighlightEnabled(false);

        view.setupDutyBarChart(barDutyData, xAxisValueFormatter, yAxisValueFormatter);
    }

    private void processFlightHours() {
        List<BarEntry> barFlightEntries = new ArrayList<>();

        float xValueCount = 0f;

        long startMonth = new DateTime(flights.minDate("startFlightTime")).getMonthOfYear();

        long currentYear = 0;
        long currentMonth = 0;

        for (Flight flight : flights) {
            DateTime startDateTime = new DateTime(flight.getStartFlightTime());
            DateTime endDateTime = new DateTime(flight.getEndFlightTime());

            float decHoursDiff = (Seconds.secondsBetween(startDateTime, endDateTime).getSeconds() / 60f) / 60f;

            if (currentYear == 0 && currentMonth == 0) {
                currentYear = startDateTime.getYear();
                currentMonth = startDateTime.getMonthOfYear();

                //Add new entry using day of month as x value
                BarEntry newEntry = new BarEntry(xValueCount, decHoursDiff);
                barFlightEntries.add(newEntry);
                xValueCount++;
            } else {
                //Check if year wraps around
                if (currentMonth + 1 < 13) {
                    //Add on any skipped months to xValueCount
                    if (currentMonth + 1 != startDateTime.getMonthOfYear()) {
                        xValueCount += (startDateTime.getMonthOfYear() - currentMonth) - 1;
                    }
                }
                //Check if the month is the same as the previous flight
                // All flights are provided in an ordered list by the repo
                if (currentMonth == startDateTime.getMonthOfYear()
                        && currentYear == startDateTime.getYear()) {
                    //Get last entry in list
                    BarEntry lastEntry = barFlightEntries.get(barFlightEntries.size() - 1);
                    //Add the additional hours in that day on, X value (the date) does not change
                    lastEntry = new BarEntry(lastEntry.getX(), lastEntry.getY() + decHoursDiff);
                    //Remove the last entry and add the modified entry instead of it
                    barFlightEntries.remove(barFlightEntries.size() - 1);
                    barFlightEntries.add(lastEntry);
                } else {
                    //Check if days of month wrap around
                    if (startDateTime.getYear() != currentYear) {

                    }

                    //New month
                    //Update these for the next iteration
                    currentYear = startDateTime.getYear();
                    currentMonth = startDateTime.getMonthOfYear();

                    //Add new entry using day of month as x value
                    BarEntry newEntry = new BarEntry(xValueCount, decHoursDiff);
                    barFlightEntries.add(newEntry);
                    xValueCount++;
                }
            }
        }

        IAxisValueFormatter xAxisValueFormatter = new MonthAxisValueFormatter(((int) startMonth - 1));
        IAxisValueFormatter yAxisValueFormatter = new DefaultAxisValueFormatter(2);
        BarDataSet dataSet = new BarDataSet(barFlightEntries, "Flight Hours");
        dataSet.setValueTextSize(16f);
        BarData barDutyData = new BarData(dataSet);
        barDutyData.setBarWidth(0.9f);
        barDutyData.setHighlightEnabled(false);

        view.setupFlightBarChart(barDutyData, xAxisValueFormatter, yAxisValueFormatter);
    }
}
