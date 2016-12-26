package com.aliakseipilko.flightdutytracker.realm.repository.impl;

import android.support.annotation.NonNull;

import com.aliakseipilko.flightdutytracker.realm.model.Flight;
import com.aliakseipilko.flightdutytracker.realm.repository.IFlightRepository;
import com.aliakseipilko.flightdutytracker.utils.AirportCode;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmResults;

import static com.aliakseipilko.flightdutytracker.utils.AirportCode.CODE_TYPES.ICAO_CODE;

public class FlightRepository implements IFlightRepository {

    @Inject Realm realm;

    @Override
    public void addFlight(final Flight flight, @NonNull final OnAddFlightCallback callback) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(flight);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                callback.OnSuccess();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                callback.OnError(error.getMessage());
            }
        });
    }

    @Override
    public void deleteFlight(final Flight flight, @NonNull final OnDeleteFlightCallback callback) {
        //Find the flight by Primary key ID field first
        final RealmResults<Flight> results = realm.where(Flight.class)
                .equalTo("id", flight.getId())
                .findAll();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                //ID is unique, so results should contain at most 1 match, hence deleting all would be same as delete first/last/an index
                results.deleteAllFromRealm();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                callback.OnSuccess();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                callback.OnError(error.getMessage());
            }
        });
    }

    @Override
    public void deleteFlightByFlightNumber(String flightNumber, @NonNull final OnDeleteFlightCallback callback) {
        final RealmResults<Flight> results = realm.where(Flight.class)
                .equalTo("flightNumber", flightNumber)
                .findAll();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                //ID is unique, so results should contain at most 1 match, hence deleting all would be same as delete first/last/an index
                results.deleteAllFromRealm();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                callback.OnSuccess();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                callback.OnError(error.getMessage());
            }
        });
    }

    @Override
    public void deleteFlightById(long id, @NonNull final OnDeleteFlightCallback callback) {
        final RealmResults<Flight> results = realm.where(Flight.class)
                .equalTo("id", id)
                .findAll();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                //ID is unique, so results should contain at most 1 match, hence deleting all would be same as delete first/last/an index
                results.deleteAllFromRealm();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                callback.OnSuccess();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                callback.OnError(error.getMessage());
            }
        });
    }

    @Override
    public void getSingleFlightById(long id, @NonNull OnGetSingleFlightCallback callback) {
        Flight result = realm.where(Flight.class)
                .equalTo("id", id)
                .findFirst();

        if (result.isLoaded()) {
            callback.OnSuccess(result);
        }else{
            callback.OnError("Flight not loaded");
        }
    }

    @Override
    public void getMultipleFlightsByFlightNumber(String flightNumber, @NonNull OnGetMultipleFlightsCallback callback) {
        RealmResults<Flight> results = realm.where(Flight.class)
                .equalTo("flightNumber", flightNumber)
                .findAll();

        if (results.isLoaded()) {
            callback.OnSuccess(results);
        }else{
            callback.OnError("Flights not loaded");
        }
    }

    @Override
    public void getMultipleFlightsByACType(String ACType, @NonNull OnGetMultipleFlightsCallback callback) {
        RealmResults<Flight> results = realm.where(Flight.class)
                .equalTo("acType", ACType)
                .findAll();

        if (results.isLoaded()) {
            callback.OnSuccess(results);
        }else{
            callback.OnError("Flights not loaded");
        }
    }

    @Override
    public void getMultipleFlightsByAirportCode(AirportCode.CODE_TYPES codeType, AirportCode.CODE_PLACES codePlace, String airportCode, @NonNull OnGetMultipleFlightsCallback callback) {

        RealmResults<Flight> results;

        if(codePlace == null){
            if(codeType == ICAO_CODE) {
                results = realm.where(Flight.class)
                        .equalTo("departureICAOCode", airportCode)
                        .or()
                        .equalTo("arrivalICAOCode", airportCode)
                        .findAll();
            }else{
                results = realm.where(Flight.class)
                        .equalTo("departureIATACode", airportCode)
                        .or()
                        .equalTo("arrivalIATACode", airportCode)
                        .findAll();
            }
        }else if(codeType == null){
            if(codePlace == AirportCode.CODE_PLACES.DEPARTURE_CODE){
                results = realm.where(Flight.class)
                        .equalTo("departureICAOCode", airportCode)
                        .or()
                        .equalTo("departureIATACode", airportCode)
                        .findAll();
            }else{
                results = realm.where(Flight.class)
                        .equalTo("arrivalICAOCode", airportCode)
                        .or()
                        .equalTo("arrivalIATACode", airportCode)
                        .findAll();
            }
        }else{
            if(codeType == ICAO_CODE){
                if(codePlace == AirportCode.CODE_PLACES.DEPARTURE_CODE){
                    results = realm.where(Flight.class)
                            .equalTo("departureICAOCode", airportCode)
                            .findAll();
                }else{
                    results = realm.where(Flight.class)
                            .equalTo("arrivalICAOCode", airportCode)
                            .findAll();
                }
            }else{
                if(codePlace == AirportCode.CODE_PLACES.DEPARTURE_CODE){
                    results = realm.where(Flight.class)
                            .equalTo("departureIATACode", airportCode)
                            .findAll();
                }else{
                    results = realm.where(Flight.class)
                            .equalTo("arrivalIATACode", airportCode)
                            .findAll();
                }
            }
        }

        if(results.isLoaded()){
            callback.OnSuccess(results);
        }else{
            callback.OnError("Flights not loaded");
        }
    }

    @Override
    public void getAllFlights(@NonNull OnGetMultipleFlightsCallback callback) {
        RealmResults<Flight> results = realm.where(Flight.class).findAll();

        if(results.isLoaded()){
            callback.OnSuccess(results);
        }else{
            callback.OnError("Flights not loaded");
        }
    }
}