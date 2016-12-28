package com.aliakseipilko.flightdutytracker.presenter;

import com.aliakseipilko.flightdutytracker.realm.model.Flight;

import java.util.Date;

public interface ICreateFlightPresenter extends IBasePresenter {

    void addFlight(Flight flight);

    void addFlight(String departureIATACode, String departureICAOCode, String arrivalIATACode, String arrivalICAOCode, Date startDutyTime, Date startFlightTime, Date endFlightTime, Date endDutyTime, String acType, String flightNumber);

}
