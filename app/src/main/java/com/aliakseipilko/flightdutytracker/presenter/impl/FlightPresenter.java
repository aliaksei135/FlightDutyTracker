package com.aliakseipilko.flightdutytracker.presenter.impl;

import android.view.View;

import com.aliakseipilko.flightdutytracker.presenter.IFlightPresenter;
import com.aliakseipilko.flightdutytracker.realm.model.Flight;
import com.aliakseipilko.flightdutytracker.realm.repository.IFlightRepository;
import com.aliakseipilko.flightdutytracker.realm.repository.impl.FlightRepository;
import com.aliakseipilko.flightdutytracker.utils.AirportCode;

import java.util.Date;

import io.realm.RealmResults;


public class FlightPresenter implements IFlightPresenter {

    private IFlightRepository.OnAddFlightCallback addFlightCallback;
    private IFlightRepository.OnGetMultipleFlightsCallback getMultipleFlightsCallback;
    private IFlightRepository.OnGetSingleFlightCallback getSingleFlightCallback;
    private IFlightRepository.OnDeleteFlightCallback deleteFlightCallback;

    private View view;

    private FlightRepository repository;

    public FlightPresenter(View view) {
        this.view = view;
        repository = new FlightRepository();
    }

    @Override
    public void addFlight(Flight flight) {

        repository.addFlight(flight, addFlightCallback);
    }

    @Override
    public void addFlight(String departureIATACode, String departureICAOCode, String arrivalIATACode, String arrivalICAOCode, Date startDutyTime, Date startFlightTime, Date endFlightTime, Date endDutyTime, String acType, String flightNumber) {
        Flight flight = new Flight();

        flight.setId(repository.getNextID());
        flight.setDepartureIATACode(departureIATACode);
        flight.setDepartureICAOCode(departureICAOCode);
        flight.setArrivalIATACode(arrivalIATACode);
        flight.setArrivalICAOCode(arrivalICAOCode);
        flight.setStartDutyTime(startDutyTime);
        flight.setStartFlightTime(startFlightTime);
        flight.setEndFlightTime(endFlightTime);
        flight.setEndDutyTime(endDutyTime);
        flight.setAcType(acType);
        flight.setFlightNumber(flightNumber);

        repository.addFlight(flight, addFlightCallback);
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
    public void getSingleFlightById(long id) {

        repository.getSingleFlightById(id, getSingleFlightCallback);
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
    public void subscribeAllCallbacks() {
        //TODO impl callbacks
        addFlightCallback = new IFlightRepository.OnAddFlightCallback() {
            @Override
            public void OnSuccess() {

            }

            @Override
            public void OnError(String message) {

            }
        };

        getMultipleFlightsCallback = new IFlightRepository.OnGetMultipleFlightsCallback() {
            @Override
            public void OnSuccess(RealmResults<Flight> flights) {

            }

            @Override
            public void OnError(String message) {

            }
        };

        getSingleFlightCallback = new IFlightRepository.OnGetSingleFlightCallback() {
            @Override
            public void OnSuccess(Flight flight) {

            }

            @Override
            public void OnError(String message) {

            }
        };

        deleteFlightCallback = new IFlightRepository.OnDeleteFlightCallback() {
            @Override
            public void OnSuccess() {

            }

            @Override
            public void OnError(String message) {

            }
        };
    }

    @Override
    public void unsubscribeAllCallbacks() {

        addFlightCallback = null;
        getMultipleFlightsCallback = null;
        getSingleFlightCallback = null;
        deleteFlightCallback = null;
    }
}
