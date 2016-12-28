package com.aliakseipilko.flightdutytracker.presenter;

public interface IFlightDetailsPresenter extends IBasePresenter {

    void getSingleFlightById(long id);

    void deleteFlightById(long id);
}
