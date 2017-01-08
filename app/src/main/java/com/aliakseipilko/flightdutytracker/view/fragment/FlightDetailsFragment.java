/*
 * Copyright (C) 2017 Aliaksei Pilko
 *
 * Licensed under the Apache License, Version 2.0 (the “License”); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an “AS IS” BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package com.aliakseipilko.flightdutytracker.view.fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aliakseipilko.flightdutytracker.R;
import com.aliakseipilko.flightdutytracker.dagger.components.DaggerStorageComponent;
import com.aliakseipilko.flightdutytracker.dagger.modules.PrefsModule;
import com.aliakseipilko.flightdutytracker.presenter.impl.FlightDetailsPresenter;
import com.aliakseipilko.flightdutytracker.realm.model.Flight;
import com.aliakseipilko.flightdutytracker.view.activity.EditFlightActivity;
import com.aliakseipilko.flightdutytracker.view.activity.FlightDetailsActivity;
import com.aliakseipilko.flightdutytracker.view.fragment.base.BaseFragment;

import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FlightDetailsFragment extends BaseFragment {

    private static long flightId;
    @BindView(R.id.detail_departure_tv)
    TextView departureTv;
    @BindView(R.id.detail_arrival_tv)
    TextView arrivalTv;
    @BindView(R.id.detail_ac_tv)
    TextView acTypeTv;
    @BindView(R.id.detail_duty_from_date_tv)
    TextView dutyFromTv;
    @BindView(R.id.detail_duty_to_date_tv)
    TextView dutyToTv;
    @BindView(R.id.detail_flight_from_date_tv)
    TextView flightFromTv;
    @BindView(R.id.detail_flight_to_date_tv)
    TextView flightToTv;
    @Inject
    SharedPreferences prefs;
    private FlightDetailsPresenter presenter;
    private Unbinder unbinder;
    private Flight flight;
    private FlightDetailsActivity activityCallback;

    public FlightDetailsFragment() {

    }

    public static FlightDetailsFragment newInstance(long flightId) {
        FlightDetailsFragment.flightId = flightId;
        return new FlightDetailsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerStorageComponent.builder().prefsModule(new PrefsModule(getContext().getApplicationContext())).build().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_flight_details, container, false);
        unbinder = ButterKnife.bind(this, view);

        presenter = new FlightDetailsPresenter(this);
        presenter.subscribeAllCallbacks();
        presenter.getSingleFlightById(flightId);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void displayFlight() {

        activityCallback.setToolbarTitle(flight.getFlightNumber());

        if (prefs.getString("airportCodeType", "IATA").equals("ICAO")) {
            departureTv.setText(flight.getDepartureICAOCode());
            arrivalTv.setText(flight.getArrivalICAOCode());
        } else {
            departureTv.setText(flight.getDepartureIATACode());
            arrivalTv.setText(flight.getArrivalIATACode());
        }
        acTypeTv.setText(flight.getAcType());

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm", Locale.getDefault());
        dutyFromTv.setText(sdf.format(flight.getStartDutyTime()));
        dutyToTv.setText(sdf.format(flight.getEndDutyTime()));
        flightFromTv.setText(sdf.format(flight.getStartFlightTime()));
        flightToTv.setText(sdf.format(flight.getEndFlightTime()));
    }

    public void deleteFlight() {
        presenter.deleteFlightById(flightId);
    }

    @Override
    public void onFABClicked() {
        Intent intent = new Intent(getActivity(), EditFlightActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.putExtra("flightId", flightId);
        startActivity(intent);
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
        displayFlight();
    }

    public void setInterface(FlightDetailsActivity activityCallback) {
        this.activityCallback = activityCallback;
    }

    public interface FlightDetailsFragmentInterface {
        void setToolbarTitle(String title);
    }

}
