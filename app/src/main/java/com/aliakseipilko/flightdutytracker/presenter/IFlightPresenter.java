package com.aliakseipilko.flightdutytracker.presenter;

import com.aliakseipilko.flightdutytracker.realm.model.Flight;
import com.aliakseipilko.flightdutytracker.utils.AirportCode;

public interface IFlightPresenter extends IBasePresenter {

    void addFlight(Flight flight);

    void deleteFlightById(long id);

    void deleteFlight(Flight flight);

    void deleteFlightByFlightNumber(String flightNumber);

    void getSingleFlightById(long id);

    void getMultipleFlightsByFlightNumber(String flightNumber);

    void getMultipleFlightsByACType(String ACType);

    void getMultipleFlightsByAirportCode(AirportCode.CODE_TYPES codeType, AirportCode.CODE_PLACES codePlace, String airportCode);

    void getAllFlights();
}
