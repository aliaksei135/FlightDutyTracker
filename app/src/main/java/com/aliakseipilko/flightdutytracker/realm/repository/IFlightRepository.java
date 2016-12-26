package com.aliakseipilko.flightdutytracker.realm.repository;

import android.support.annotation.NonNull;

import com.aliakseipilko.flightdutytracker.realm.model.Flight;
import com.aliakseipilko.flightdutytracker.utils.AirportCode;

import io.realm.RealmResults;

public interface IFlightRepository extends IBaseRepository{

    interface OnAddFlightCallback{
        void OnSuccess();
        void OnError(String message);
    }

    interface OnGetMultipleFlightsCallback{
        void OnSuccess(RealmResults<Flight> flights);
        void OnError(String message);
    }

    interface OnGetSingleFlightCallback{
        void OnSuccess(Flight flight);
        void OnError(String message);
    }

    interface OnDeleteFlightCallback{
        void OnSuccess();
        void OnError(String message);
    }

    void addFlight(Flight flight, @NonNull OnAddFlightCallback callback);

    void deleteFlight(Flight flight,@NonNull OnDeleteFlightCallback callback);

    void deleteFlightByFlightNumber(String flightNumber, @NonNull OnDeleteFlightCallback callback);

    void deleteFlightById(long id, @NonNull OnDeleteFlightCallback callback);

    void getSingleFlightById(long id, @NonNull OnGetSingleFlightCallback callback);

    void getMultipleFlightsByFlightNumber(String flightNumber, @NonNull OnGetMultipleFlightsCallback callback);

    void getMultipleFlightsByACType(String ACType, @NonNull OnGetMultipleFlightsCallback callback);

    void getMultipleFlightsByAirportCode(AirportCode.CODE_TYPES codeType, AirportCode.CODE_PLACES codePlace, String airportCode, @NonNull OnGetMultipleFlightsCallback callback);

    void getAllFlights(@NonNull OnGetMultipleFlightsCallback callback);
}
