package com.aliakseipilko.flightdutytracker.presenter;


import com.aliakseipilko.flightdutytracker.utils.AirportCode;

import java.util.Date;

public interface IStatsPresenter extends IBasePresenter {

    void getMultipleFlightsByACType(String ACType);

    void getMultipleFlightsByAirportCode(AirportCode.CODE_TYPES codeType, AirportCode.CODE_PLACES codePlace, String airportCode);

    void getAllFlights();

    void getMultipleFlightsByIdCount(long startId, int count);

    void getMultipleFlightsByDateRange(Date startDate, Date endDate);
}
