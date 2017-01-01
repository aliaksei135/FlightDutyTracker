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
        dateTime.minusDays(days);
        Date startDate = dateTime.toDate();

        repository.getMultipleFlightsByDateRange(startDate, new Date(), getMultipleFlightsDutyHoursCallback);
    }

    @Override
    public void getFlightHoursInPastDays(int days) {

        DateTime dateTime = DateTime.now();
        dateTime.minusDays(days);
        Date startDate = dateTime.toDate();

        repository.getMultipleFlightsByDateRange(startDate, new Date(), getMultipleFlightsFlightHoursCallback);
    }

    @Override
    public void subscribeAllCallbacks() {
        getMultipleFlightsDutyHoursCallback = new IFlightRepository.OnGetMultipleFlightsCallback() {
            @Override
            public void OnSuccess(RealmResults<Flight> flights) {
                long dutyMillis = 0;

                for (Flight flight : flights) {
                    dutyMillis += (flight.getEndDutyTime().getTime() - flight.getStartDutyTime().getTime());
                }

                Duration dutyDuration = Duration.millis(dutyMillis);
                long hours = dutyDuration.getStandardHours();
                long minutes = dutyDuration.minus(hours * 60 * 60 * 1000).getStandardMinutes();
                view.showDutyHours(hours + ":" + minutes, dutyMillis);
            }

            @Override
            public void OnError(String message) {
                view.showError(message);
            }
        };
        getMultipleFlightsFlightHoursCallback = new IFlightRepository.OnGetMultipleFlightsCallback() {
            @Override
            public void OnSuccess(RealmResults<Flight> flights) {
                long flightMillis = 0;

                for (Flight flight : flights) {
                    flightMillis += (flight.getEndFlightTime().getTime() - flight.getStartFlightTime().getTime());
                }

                Duration dutyDuration = Duration.millis(flightMillis);
                long hours = dutyDuration.getStandardHours();
                long minutes = dutyDuration.minus(hours * 60 * 60 * 1000).getStandardMinutes();
                view.showFlightHours(hours + ":" + minutes, flightMillis);
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
