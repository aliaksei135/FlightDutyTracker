package com.aliakseipilko.flightdutytracker.presenter.impl;

import com.aliakseipilko.flightdutytracker.presenter.IFlightPresenter;
import com.aliakseipilko.flightdutytracker.realm.model.Flight;
import com.aliakseipilko.flightdutytracker.realm.repository.IFlightRepository;
import com.aliakseipilko.flightdutytracker.realm.repository.impl.FlightRepository;
import com.aliakseipilko.flightdutytracker.utils.AirportCode;
import com.aliakseipilko.flightdutytracker.view.adapter.FlightAdapter;
import com.aliakseipilko.flightdutytracker.view.fragment.FlightFragment;

import java.util.Calendar;
import java.util.Date;

import io.realm.RealmResults;


public class FlightPresenter implements IFlightPresenter {

    private IFlightRepository.OnGetMultipleFlightsCallback getMultipleFlightsCallback;
    private IFlightRepository.OnDeleteFlightCallback deleteFlightCallback;

    private FlightFragment view;
    private FlightAdapter adapter;

    private FlightRepository repository;

    public FlightPresenter(FlightFragment view) {

        this.view = view;
        repository = new FlightRepository();
    }

    public void initAdapaterData() {

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -7);
        //Get all flights between today and 7 days ago to start with
        adapter = new FlightAdapter(view.getContext(), null);
        view.setAdapter(adapter);
        getMultipleFlightsByDateRange(new Date(), cal.getTime());
    }

    @Override
    public void deleteFlightById(long id) {

        repository.deleteFlightById(id, deleteFlightCallback);
    }

    @Override
    public void deleteFlight(Flight flight) {

        repository.deleteFlight(flight, deleteFlightCallback);
    }

    @Override
    public void deleteFlightByFlightNumber(String flightNumber) {

        repository.deleteFlightByFlightNumber(flightNumber, deleteFlightCallback);
    }

    @Override
    public void getMultipleFlightsByFlightNumber(String flightNumber) {

        repository.getMultipleFlightsByFlightNumber(flightNumber, getMultipleFlightsCallback);
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

        repository.getMultipleFlightsByIdCount(startId, count, getMultipleFlightsCallback);
    }

    @Override
    public void getMultipleFlightsByDateRange(Date startDate, Date endDate) {

//        repository.getMultipleFlightsByDateRange(startDate, endDate, getMultipleFlightsCallback);
        repository.getAllFlights(getMultipleFlightsCallback);
    }


    @Override
    public void subscribeAllCallbacks() {

        getMultipleFlightsCallback = new IFlightRepository.OnGetMultipleFlightsCallback() {
            @Override
            public void OnSuccess(RealmResults<Flight> flights) {
                adapter.addMoreData(flights);
            }

            @Override
            public void OnError(String message) {
                view.showError(message);
            }
        };

        deleteFlightCallback = new IFlightRepository.OnDeleteFlightCallback() {
            @Override
            public void OnSuccess() {
                view.showSuccess("Successfully deleted!");
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
        deleteFlightCallback = null;
    }
}
