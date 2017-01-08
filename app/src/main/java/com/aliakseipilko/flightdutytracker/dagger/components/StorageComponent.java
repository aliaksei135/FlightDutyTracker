/*
 * Copyright (C) 2017 Aliaksei Pilko
 *
 * Licensed under the Apache License, Version 2.0 (the “License”); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an “AS IS” BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package com.aliakseipilko.flightdutytracker.dagger.components;

import com.aliakseipilko.flightdutytracker.dagger.modules.PrefsModule;
import com.aliakseipilko.flightdutytracker.dagger.modules.RealmModule;
import com.aliakseipilko.flightdutytracker.realm.repository.impl.FlightRepository;
import com.aliakseipilko.flightdutytracker.view.adapter.FlightAdapter;
import com.aliakseipilko.flightdutytracker.view.fragment.EditFlightFragment;
import com.aliakseipilko.flightdutytracker.view.fragment.FlightDetailsFragment;
import com.aliakseipilko.flightdutytracker.view.fragment.SettingsFragment;
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

    void inject(FlightRepository repository);

    void inject(SettingsFragment fragment);

    void inject(EditFlightFragment fragment);

    void inject(FlightDetailsFragment fragment);

    void inject(FlightAdapter flightAdapter);
}
