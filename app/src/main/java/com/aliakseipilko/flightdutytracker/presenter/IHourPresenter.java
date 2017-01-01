package com.aliakseipilko.flightdutytracker.presenter;


public interface IHourPresenter extends IBasePresenter {

    void getDutyHoursInPastDays(int days);

    void getFlightHoursInPastDays(int days);

}
