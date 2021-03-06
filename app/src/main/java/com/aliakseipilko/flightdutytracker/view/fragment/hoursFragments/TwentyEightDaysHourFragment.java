/*
 * Copyright (C) 2017 Aliaksei Pilko
 *
 * Licensed under the Apache License, Version 2.0 (the “License”); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an “AS IS” BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package com.aliakseipilko.flightdutytracker.view.fragment.hoursFragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aliakseipilko.flightdutytracker.R;
import com.aliakseipilko.flightdutytracker.dagger.components.DaggerStorageComponent;
import com.aliakseipilko.flightdutytracker.dagger.components.StorageComponent;
import com.aliakseipilko.flightdutytracker.dagger.modules.PrefsModule;
import com.aliakseipilko.flightdutytracker.presenter.impl.HourPresenter;
import com.aliakseipilko.flightdutytracker.view.fragment.HourFragment;
import com.aliakseipilko.flightdutytracker.view.fragment.hoursFragments.base.BaseHourFragment;
import com.aliakseipilko.flightdutytracker.view.views.MarkView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class TwentyEightDaysHourFragment extends BaseHourFragment {

    @BindView(R.id.twenty_eight_day_duty_hours)
    MarkView dutyHoursMarkView;
    @BindView(R.id.twenty_eight_day_flight_hours)
    MarkView flightHoursMarkView;

    Unbinder unbinder;
    StorageComponent storageComponent;
    @Inject
    SharedPreferences prefs;
    double maxDutyMillis, maxFlightMillis;
    private HourPresenter presenter;

    public TwentyEightDaysHourFragment() {
        // Required empty public constructor
    }

    public static TwentyEightDaysHourFragment newInstance() {

        return new TwentyEightDaysHourFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_twenty_eight_days_hour, container, false);
        unbinder = ButterKnife.bind(this, view);

        storageComponent = DaggerStorageComponent.builder().prefsModule(new PrefsModule(getContext().getApplicationContext())).build();
        storageComponent.inject(this);

        maxDutyMillis = prefs.getInt("max28DayDutyHours", 190) * 60 * 60 * 1000;
        maxFlightMillis = prefs.getInt("max28DayFlightHours", 100) * 60 * 60 * 1000;

        presenter = new HourPresenter(this);
        presenter.subscribeAllCallbacks();
        presenter.getFlightHoursInPastDays(7);
        presenter.getDutyHoursInPastDays(7);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showDutyHours(String s, double dutyMillis) {
        dutyHoursMarkView.setMax(maxDutyMillis);
        dutyHoursMarkView.setMark(dutyMillis);
        dutyHoursMarkView.setText(s);
    }

    @Override
    public void showFlightHours(String s, double flightMillis) {
        flightHoursMarkView.setMax(maxFlightMillis);
        flightHoursMarkView.setMark(flightMillis);
        flightHoursMarkView.setText(s);
    }

    @Override
    public void showError(String message) {
        ((HourFragment) getParentFragment()).showError(message);
    }
}
