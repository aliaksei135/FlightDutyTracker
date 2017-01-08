package com.aliakseipilko.flightdutytracker.dagger.modules;

import com.google.gson.Gson;

import android.content.Context;

import com.aliakseipilko.flightdutytracker.utils.AirportCodeConverter;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = {GsonModule.class})
public class AirportCodeConverterModule {

    Gson gson;
    Context appContext;

    @Inject
    public AirportCodeConverterModule(Context appContext, Gson gson) {
        this.appContext = appContext;
        this.gson = gson;
    }

    @Provides
    @Singleton
    public AirportCodeConverter provideAirportCodeConverter() {

        return new AirportCodeConverter(appContext, gson);
    }
}
