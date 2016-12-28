package com.aliakseipilko.flightdutytracker.presenter.impl;

import com.aliakseipilko.flightdutytracker.presenter.IFlightPresenter;
import com.aliakseipilko.flightdutytracker.realm.model.Flight;
import com.aliakseipilko.flightdutytracker.realm.repository.IFlightRepository;
import com.aliakseipilko.flightdutytracker.realm.repository.impl.FlightRepository;
import com.aliakseipilko.flightdutytracker.utils.AirportCode;
import com.aliakseipilko.flightdutytracker.view.adapter.FlightAdapter;
import com.aliakseipilko.flightdutytracker.view.fragment.FlightFragment;

import java.util.Date;

import io.realm.OrderedRealmCollection;
import io.realm.RealmList;
import io.realm.RealmResults;


public class FlightPresenter implements IFlightPresenter {

    private IFlightRepository.OnAddFlightCallback addFlightCallback;
    private IFlightRepository.OnGetMultipleFlightsCallback getMultipleFlightsCallback;
    private IFlightRepository.OnGetSingleFlightCallback getSingleFlightCallback;
    private IFlightRepository.OnDeleteFlightCallback deleteFlightCallback;

    private FlightFragment view;
    private FlightAdapter adapter;

    private FlightRepository repository;

    public FlightPresenter(FlightFragment view, FlightAdapter adapter) {

        this.view = view;
        this.adapter = adapter;
        repository = new FlightRepository();
    }

    public void addFlight(Flight flight) {

        repository.addFlight(flight, addFlightCallback);
    }

    public void addFlight(String departureIATACode,
                          String departureICAOCode,
                          String arrivalIATACode,
                          String arrivalICAOCode,
                          Date startDutyTime,
                          Date startFlightTime,
                          Date endFlightTime,
                          Date endDutyTime,
                          String acType,
                          String flightNumber) {

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
    public void getMultipleFlightsByIdCount(long startId, int count) {

        repository.getMultipleFlightsByIdCount(startId, count, getMultipleFlightsCallback);
    }

    @Override
    public void getMultipleFlightsByDateRange(Date startDate, Date endDate) {

        repository.getMultipleFlightsByDateRange(startDate, endDate, getMultipleFlightsCallback);
    }

    @Override
    public void subscribeAllCallbacks() {

        addFlightCallback = new IFlightRepository.OnAddFlightCallback() {
            @Override
            public void OnSuccess() {
                view.showSuccess("Successfully Added!");
            }

            @Override
            public void OnError(String message) {
                view.showError(message);
            }
        };

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

        getSingleFlightCallback = new IFlightRepository.OnGetSingleFlightCallback() {
            @Override
            public void OnSuccess(Flight flight) {
                OrderedRealmCollection<Flight> flights = new RealmList<>(flight);
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

        addFlightCallback = null;
        getMultipleFlightsCallback = null;
        getSingleFlightCallback = null;
        deleteFlightCallback = null;
    }
}
