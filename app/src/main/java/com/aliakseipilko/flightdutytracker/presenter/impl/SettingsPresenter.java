package com.aliakseipilko.flightdutytracker.presenter.impl;

import android.content.SharedPreferences;
import android.view.View;

import com.aliakseipilko.flightdutytracker.dagger.components.DaggerStorageComponent;
import com.aliakseipilko.flightdutytracker.dagger.components.StorageComponent;
import com.aliakseipilko.flightdutytracker.dagger.modules.PrefsModule;
import com.aliakseipilko.flightdutytracker.presenter.ISettingsPresenter;

import javax.inject.Inject;


public class SettingsPresenter implements ISettingsPresenter {

    @Inject
    SharedPreferences prefs;

    private View view;

    public SettingsPresenter(View view) {
        this.view = view;
        StorageComponent component = DaggerStorageComponent.builder().prefsModule(new PrefsModule(view.getContext())).build();
        component.inject(this);
    }

    @Override
    public void setHours(String key, double hours) {
        prefs.edit().putLong(key, Double.doubleToLongBits(hours)).apply();
    }

    @Override
    public void setPref(String key, String value) {
        prefs.edit().putString(key, value).apply();
    }

    @Override
    public void setPref(String key, int value) {
        prefs.edit().putInt(key, value).apply();
    }

    @Override
    public void setPref(String key, float value) {
        prefs.edit().putFloat(key, value).apply();
    }

    @Override
    public void setPref(String key, long value) {
        prefs.edit().putLong(key, value).apply();
    }

    @Override
    public void subscribeAllCallbacks() {

    }

    @Override
    public void unsubscribeAllCallbacks() {

    }
}
