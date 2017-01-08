package com.aliakseipilko.flightdutytracker.dagger.components;

import com.aliakseipilko.flightdutytracker.dagger.modules.AirportCodeConverterModule;
import com.aliakseipilko.flightdutytracker.presenter.impl.CreateFlightPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AirportCodeConverterModule.class})
public interface UtilsComponent {

    void inject(CreateFlightPresenter presenter);
}
