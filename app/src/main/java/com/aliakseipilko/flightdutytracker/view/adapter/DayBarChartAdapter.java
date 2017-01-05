package com.aliakseipilko.flightdutytracker.view.adapter;


import com.aliakseipilko.flightdutytracker.realm.model.Flight;
import com.aliakseipilko.flightdutytracker.utils.DayAxisValueFormatter;
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

public class DayBarChartAdapter extends BaseChartAdapter {

    RealmResults<Flight> flights;

    BaseStatsFragment view;

    public DayBarChartAdapter(BaseStatsFragment view) {
        this.view = view;
    }

    public void process(RealmResults<Flight> flights) {
        this.flights = flights;
        processDutyHours();
        processFlightHours();
    }

    public void processDutyHours() {
        List<BarEntry> barDutyEntries = new ArrayList<>();

        float xValueCount = 0f;
        int maxDaysInMonth = 0;

        long startDay = new DateTime(flights.minDate("startDutyTime")).getDayOfMonth();

        long currentYear = 0;
        long currentMonth = 0;
        long currentDay = 0;
        boolean isMonthWrapping = false;

        for (Flight flight : flights) {

            DateTime startDateTime = new DateTime(flight.getStartDutyTime());
            DateTime endDateTime = new DateTime(flight.getEndDutyTime());

            float decHoursDiff = (Seconds.secondsBetween(startDateTime, endDateTime).getSeconds() / 60f) / 60f;

            if (currentYear == 0 && currentMonth == 0 && currentDay == 0) {
                currentYear = startDateTime.getYear();
                currentMonth = startDateTime.getMonthOfYear();
                currentDay = startDateTime.getDayOfMonth();

                maxDaysInMonth = determineDaysInMonth((int) currentMonth, (int) currentYear);

                //Add new entry using day of month as x value
                BarEntry newEntry = new BarEntry(xValueCount, decHoursDiff);
                barDutyEntries.add(newEntry);
                xValueCount++;
            } else {
                //Check if month is wrapping
                if (currentDay + 1 >= maxDaysInMonth) {
                    isMonthWrapping = true;
                } else {
                    //Check if days are adjacent
                    //Add skipped days onto xValueCount to display blank spaces in graph
                    if (currentDay + 1 != startDateTime.getDayOfMonth()) {
                        xValueCount = xValueCount + (startDateTime.getDayOfMonth() - currentDay) - 1;
                    }
                }
                //Check if the date is the same as the previous flight
                // All flights are provided in an ordered list by the repo
                if (currentDay == startDateTime.getDayOfMonth()
                        && currentMonth == startDateTime.getMonthOfYear()
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
                    if (startDateTime.getMonthOfYear() != currentMonth) {
                        isMonthWrapping = true;
                    }

                    //New day
                    //Update these for the next iteration
                    currentYear = startDateTime.getYear();
                    currentMonth = startDateTime.getMonthOfYear();
                    currentDay = startDateTime.getDayOfMonth();

                    //Add new entry using day of month as x value
                    BarEntry newEntry = new BarEntry(xValueCount, decHoursDiff);
                    barDutyEntries.add(newEntry);
                    xValueCount++;
                }
            }
        }

        IAxisValueFormatter xAxisValueFormatter = new DayAxisValueFormatter(isMonthWrapping, (int) (startDay - 1), maxDaysInMonth);
        IAxisValueFormatter yAxisValueFormatter = new DefaultAxisValueFormatter(2);
        BarDataSet dataSet = new BarDataSet(barDutyEntries, "Duty Hours");
        dataSet.setValueTextSize(18f);
        BarData barDutyData = new BarData(dataSet);
        barDutyData.setBarWidth(0.9f);
        barDutyData.setValueTextSize(18f);
        barDutyData.setHighlightEnabled(false);

        view.setupDutyBarChart(barDutyData, xAxisValueFormatter, yAxisValueFormatter);
    }

    public void processFlightHours() {
        List<BarEntry> barFlightEntries = new ArrayList<>();

        float xValueCount = 0f;
        int maxDaysInMonth = 0;

        long startDay = new DateTime(flights.minDate("startFlightTime")).getDayOfMonth();

        long currentYear = 0;
        long currentMonth = 0;
        long currentDay = 0;
        boolean isMonthWrapping = false;

        for (Flight flight : flights) {

            DateTime startDateTime = new DateTime(flight.getStartFlightTime());
            DateTime endDateTime = new DateTime(flight.getEndFlightTime());

            float decHoursDiff = (Seconds.secondsBetween(startDateTime, endDateTime).getSeconds() / 60f) / 60f;

            if (currentYear == 0 && currentMonth == 0 && currentDay == 0) {
                currentYear = startDateTime.getYear();
                currentMonth = startDateTime.getMonthOfYear();
                currentDay = startDateTime.getDayOfMonth();

                maxDaysInMonth = determineDaysInMonth((int) currentMonth, (int) currentYear);

                //Add new entry using day of month as x value
                BarEntry newEntry = new BarEntry(xValueCount, decHoursDiff);
                barFlightEntries.add(newEntry);
                xValueCount++;
            } else {
                //Check if month is wrapping
                if (currentDay + 1 >= maxDaysInMonth) {
                    isMonthWrapping = true;
                } else {
                    //Check if days are adjacent
                    //Add skipped days onto xValueCount to display blank spaces in graph
                    if (currentDay + 1 != startDateTime.getDayOfMonth()) {
                        xValueCount = xValueCount + (startDateTime.getDayOfMonth() - currentDay) - 1;
                    }
                }
                //Check if the date is the same as the previous flight
                // All flights are provided in an ordered list by the repo
                if (currentDay == startDateTime.getDayOfMonth()
                        && currentMonth == startDateTime.getMonthOfYear()
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
                    if (startDateTime.getMonthOfYear() != currentMonth) {
                        isMonthWrapping = true;
                    }

                    //New day
                    //Update these for the next iteration
                    currentYear = startDateTime.getYear();
                    currentMonth = startDateTime.getMonthOfYear();
                    currentDay = startDateTime.getDayOfMonth();

                    //Add new entry using day of month as x value
                    BarEntry newEntry = new BarEntry(xValueCount, decHoursDiff);
                    barFlightEntries.add(newEntry);
                    xValueCount++;
                }
            }
        }

        IAxisValueFormatter xAxisValueFormatter = new DayAxisValueFormatter(isMonthWrapping, (int) (startDay - 1), maxDaysInMonth);
        IAxisValueFormatter yAxisValueFormatter = new DefaultAxisValueFormatter(2);
        BarDataSet dataSet = new BarDataSet(barFlightEntries, "Flight Hours");
        dataSet.setValueTextSize(18f);
        BarData barFlightData = new BarData(dataSet);
        barFlightData.setBarWidth(0.9f);
        barFlightData.setValueTextSize(18f);
        barFlightData.setHighlightEnabled(false);

        view.setupFlightBarChart(barFlightData, xAxisValueFormatter, yAxisValueFormatter);
    }

    int determineDaysInMonth(int month, int year) {
        switch (month) {
            case 2:
                return isLeapYear(year) ? 29 : 28;
            case 4:
                return 30;
            case 6:
                return 30;
            case 9:
                return 30;
            case 11:
                return 30;
            default:
                return 31;
        }
    }

    private boolean isLeapYear(int year) {
        if (year % 4 == 0) {
            if (year % 100 == 0) {
                if (year % 400 == 0) {
                    return true;
                }
            } else {
                return true;
            }
        }
        return false;
    }

}
