package com.aliakseipilko.flightdutytracker.presenter;

import com.aliakseipilko.flightdutytracker.realm.model.Flight;
import com.aliakseipilko.flightdutytracker.utils.AirportCode;

import java.util.Date;

public interface IFlightPresenter extends IBasePresenter {

    void addFlight(Flight flight);

    void addFlight(String departureIATACode, String departureICAOCode, String arrivalIATACode, String arrivalICAOCode, Date startDutyTime, Date startFlightTime, Date endFlightTime, Date endDutyTime, String acType, String flightNumber);

    void deleteFlightById(long id);

    void deleteFlight(Flight flight);

    void deleteFlightByFlightNumber(String flightNumber);

    void getSingleFlightById(long id);

    void getMultipleFlightsByFlightNumber(String flightNumber);

    void getMultipleFlightsByACType(String ACType);

    void getMultipleFlightsByAirportCode(AirportCode.CODE_TYPES codeType, AirportCode.CODE_PLACES codePlace, String airportCode);

    void getAllFlights();
}
