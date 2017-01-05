package com.aliakseipilko.flightdutytracker.presenter.impl;

import com.aliakseipilko.flightdutytracker.presenter.IHourPresenter;
import com.aliakseipilko.flightdutytracker.realm.model.Flight;
import com.aliakseipilko.flightdutytracker.realm.repository.IFlightRepository;
import com.aliakseipilko.flightdutytracker.realm.repository.impl.FlightRepository;
import com.aliakseipilko.flightdutytracker.view.fragment.hoursFragments.base.BaseHourFragment;

import org.joda.time.DateTime;
import org.joda.time.Duration;

import java.util.Date;

import io.realm.RealmResults;
import io.realm.Sort;


public class HourPresenter implements IHourPresenter {

    private IFlightRepository.OnGetMultipleFlightsCallback getMultipleFlightsDutyHoursCallback;
    private IFlightRepository.OnGetMultipleFlightsCallback getMultipleFlightsFlightHoursCallback;

    private FlightRepository repository;
    private BaseHourFragment view;

    public HourPresenter(BaseHourFragment view) {
        this.view = view;
        repository = new FlightRepository();
    }

    @Override
    public void getDutyHoursInPastDays(int days) {

        DateTime dateTime = DateTime.now();
        Date startDate = dateTime.minusDays(days).toDate();

        repository.getMultipleFlightsByDateRange(startDate, new Date(), Sort.DESCENDING, getMultipleFlightsDutyHoursCallback);
    }

    @Override
    public void getFlightHoursInPastDays(int days) {

        DateTime dateTime = DateTime.now();
        Date startDate = dateTime.minusDays(days).toDate();

        repository.getMultipleFlightsByDateRange(startDate, new Date(), Sort.DESCENDING, getMultipleFlightsFlightHoursCallback);
    }

    @Override
    public void subscribeAllCallbacks() {
        getMultipleFlightsDutyHoursCallback = new IFlightRepository.OnGetMultipleFlightsCallback() {
            @Override
            public void OnSuccess(RealmResults<Flight> flights) {
                double dutyMillis = 0;

                for (Flight flight : flights) {
                    dutyMillis += (flight.getEndDutyTime().getTime() - flight.getStartDutyTime().getTime());
                }

                Duration dutyDuration = Duration.millis((long) dutyMillis);
                long hours = dutyDuration.getStandardHours();
                long minutes = dutyDuration.minus(hours * 60 * 60 * 1000).getStandardMinutes();
                if (minutes < 10) {
                    view.showDutyHours(hours + ":0" + minutes, dutyMillis);
                } else {
                    view.showDutyHours(hours + ":" + minutes, dutyMillis);
                }
            }

            @Override
            public void OnError(String message) {
                view.showError(message);
            }
        };
        getMultipleFlightsFlightHoursCallback = new IFlightRepository.OnGetMultipleFlightsCallback() {
            @Override
            public void OnSuccess(RealmResults<Flight> flights) {
                double flightMillis = 0;

                for (Flight flight : flights) {
                    flightMillis += (flight.getEndFlightTime().getTime() - flight.getStartFlightTime().getTime());
                }

                Duration dutyDuration = Duration.standardSeconds((long) (flightMillis / 1000));
                long hours = dutyDuration.getStandardHours();
                long minutes = dutyDuration.minus(hours * 60 * 60 * 1000).getStandardMinutes();
                if (minutes < 10) {
                    view.showFlightHours(hours + ":0" + minutes, flightMillis);
                } else {
                    view.showFlightHours(hours + ":" + minutes, flightMillis);
                }
            }

            @Override
            public void OnError(String message) {
                view.showError(message);
            }
        };
    }

    @Override
    public void unsubscribeAllCallbacks() {
        getMultipleFlightsDutyHoursCallback = null;
        getMultipleFlightsFlightHoursCallback = null;
    }
}
