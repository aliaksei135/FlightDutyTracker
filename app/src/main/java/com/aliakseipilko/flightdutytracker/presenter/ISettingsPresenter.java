package com.aliakseipilko.flightdutytracker.presenter;


public interface ISettingsPresenter extends IBasePresenter {

    void setHours(String key, double hours);

    void setPref(String key, String value);

    void setPref(String key, int value);

    void setPref(String key, float value);

    void setPref(String key, long value);

}
