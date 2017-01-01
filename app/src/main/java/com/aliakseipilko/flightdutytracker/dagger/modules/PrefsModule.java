package com.aliakseipilko.flightdutytracker.dagger.modules;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class PrefsModule {

    private static final String PREFS_NAME = "prefs";
    private Context context;

    public PrefsModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public SharedPreferences provideSharedPreferences() {
        return context.getApplicationContext().getSharedPreferences(PREFS_NAME, 0);
    }

}
