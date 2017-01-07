package com.aliakseipilko.flightdutytracker.presenter;


import com.aliakseipilko.flightdutytracker.utils.AirportCode;

import java.util.Date;

public interface IEditFlightPresenter extends IBasePresenter {

    AirportCode.CODE_TYPES determineAirportCodeType(String departureCode, String arrivalCode);

    void editFlight(long id, String departureCode, String arrivalCode, Date startDutyTime, Date startFlightTime, Date endFlightTime, Date endDutyTime, String acType, String flightNumber);

    void getSingleFlightById(long id);
}
