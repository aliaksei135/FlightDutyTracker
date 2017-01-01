package com.aliakseipilko.flightdutytracker.dagger.components;

import android.app.Activity;

import com.aliakseipilko.flightdutytracker.dagger.modules.PrefsModule;
import com.aliakseipilko.flightdutytracker.dagger.modules.RealmModule;
import com.aliakseipilko.flightdutytracker.realm.repository.impl.FlightRepository;
import com.aliakseipilko.flightdutytracker.view.fragment.hoursFragments.CalendarYearHourFragment;
import com.aliakseipilko.flightdutytracker.view.fragment.hoursFragments.SevenDaysHourFragment;
import com.aliakseipilko.flightdutytracker.view.fragment.hoursFragments.TwentyEightDaysHourFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {RealmModule.class, PrefsModule.class})
public interface StorageComponent {

    void inject(SevenDaysHourFragment fragment);

    void inject(TwentyEightDaysHourFragment fragment);

    void inject(CalendarYearHourFragment fragment);

    void inject(Activity activity);

    void inject(FlightRepository repository);
}