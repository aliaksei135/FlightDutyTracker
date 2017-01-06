package com.aliakseipilko.flightdutytracker.presenter.impl;

import com.aliakseipilko.flightdutytracker.presenter.IStatsPresenter;
import com.aliakseipilko.flightdutytracker.realm.model.Flight;
import com.aliakseipilko.flightdutytracker.realm.repository.IFlightRepository;
import com.aliakseipilko.flightdutytracker.realm.repository.impl.FlightRepository;
import com.aliakseipilko.flightdutytracker.utils.AirportCode;
import com.aliakseipilko.flightdutytracker.view.adapter.BaseChartAdapter;
import com.aliakseipilko.flightdutytracker.view.fragment.statsFragments.base.BaseStatsFragment;

import java.util.Date;

import io.realm.RealmResults;
import io.realm.Sort;


public class StatsPresenter implements IStatsPresenter {

    private IFlightRepository.OnGetMultipleFlightsCallback getMultipleFlightsCallback;

    private BaseStatsFragment view;

    private BaseChartAdapter chartAdapter;

    private FlightRepository repository;

    public StatsPresenter(BaseStatsFragment view, BaseChartAdapter chartAdapter) {
        this.view = view;
        repository = new FlightRepository();
        this.chartAdapter = chartAdapter;
    }

    @Override
    public void getMultipleFlightsByACType(String ACType) {

        repository.getMultipleFlightsByACType(ACType, getMultipleFlightsCallback);
    }

    @Override
    public void getMultipleFlightsByAirportCode(AirportCode.CODE_TYPES codeType, AirportCode.CODE_PLACES codePlace, String airportCode) {

        repository.getMultipleFlightsByAirportCode(codeType, codePlace, airportCode, getMultipleFlightsCallback);
    }

    @Override
    public void getAllFlights() {

        repository.getAllFlights(getMultipleFlightsCallback);
    }

    @Override
    public void getMultipleFlightsByIdCount(long startId, int count) {

        repository.getMultipleFlightsByIdCount(startId, count, Sort.ASCENDING, getMultipleFlightsCallback);
    }

    @Override
    public void getMultipleFlightsByDateRange(Date startDate, Date endDate) {

        repository.getMultipleFlightsByDateRange(startDate, endDate, Sort.ASCENDING, getMultipleFlightsCallback);
    }

    @Override
    public void subscribeAllCallbacks() {
        getMultipleFlightsCallback = new IFlightRepository.OnGetMultipleFlightsCallback() {
            @Override
            public void OnSuccess(RealmResults<Flight> flights) {
                chartAdapter.process(flights);
            }

            @Override
            public void OnError(String message) {
                view.showError(message);
            }
        };
    }

    @Override
    public void unsubscribeAllCallbacks() {
        getMultipleFlightsCallback = null;
    }
}
