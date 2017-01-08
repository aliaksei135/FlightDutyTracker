/*
 * Copyright (C) 2017 Aliaksei Pilko
 *
 * Licensed under the Apache License, Version 2.0 (the “License”); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an “AS IS” BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package com.aliakseipilko.flightdutytracker.presenter.impl;

import com.aliakseipilko.flightdutytracker.presenter.IFlightPresenter;
import com.aliakseipilko.flightdutytracker.realm.model.Flight;
import com.aliakseipilko.flightdutytracker.realm.repository.IFlightRepository;
import com.aliakseipilko.flightdutytracker.realm.repository.impl.FlightRepository;
import com.aliakseipilko.flightdutytracker.utils.AirportCode;
import com.aliakseipilko.flightdutytracker.view.adapter.FlightAdapter;
import com.aliakseipilko.flightdutytracker.view.fragment.FlightFragment;

import org.joda.time.DateTime;

import java.util.Date;

import io.realm.RealmResults;
import io.realm.Sort;


public class FlightPresenter implements IFlightPresenter {

    private IFlightRepository.OnGetMultipleFlightsCallback getMultipleFlightsCallback;
    private IFlightRepository.OnDeleteFlightCallback deleteFlightCallback;

    private FlightFragment view;
    private FlightAdapter adapter;

    private FlightRepository repository;

    public FlightPresenter(FlightFragment view) {

        this.view = view;
        repository = new FlightRepository();
    }

    public void initAdapaterData() {

        adapter = new FlightAdapter(view, null);
        view.setAdapter(adapter);
        getMultipleFlightsByDateRange(DateTime.now().minusDays(7).toDate(), new Date());
    }

    @Override
    public void deleteFlightById(long id) {

        repository.deleteFlightById(id, deleteFlightCallback);
    }

    @Override
    public void deleteFlight(Flight flight) {

        repository.deleteFlight(flight, deleteFlightCallback);
    }

    @Override
    public void deleteFlightByFlightNumber(String flightNumber) {

        repository.deleteFlightByFlightNumber(flightNumber, deleteFlightCallback);
    }

    @Override
    public void getMultipleFlightsByFlightNumber(String flightNumber) {

        repository.getMultipleFlightsByFlightNumber(flightNumber, getMultipleFlightsCallback);
    }

    @Override
    public void getMultipleFlightsByACType(String ACType) {

        repository.getMultipleFlightsByACType(ACType, getMultipleFlightsCallback);
    }

    @Override
    public void getMultipleFlightsByAirportCode(AirportCode.CODE_TYPES codeType, AirportCode.CODE_PLACES codePlace, String airportCode) {

        repository.getMultipleFlightsByAirportCode(codeType, codePlace, airportCode, getMultipleFlightsCallback);
    }

    @Override
    public void getAllFlights() {

        repository.getAllFlights(getMultipleFlightsCallback);
    }

    @Override
    public void getMultipleFlightsByIdCount(long startId, int count) {

        repository.getMultipleFlightsByIdCount(startId, count, Sort.DESCENDING, getMultipleFlightsCallback);
    }

    @Override
    public void getMultipleFlightsByDateRange(Date startDate, Date endDate) {

        repository.getMultipleFlightsByDateRange(startDate, endDate, Sort.DESCENDING, getMultipleFlightsCallback);
    }


    @Override
    public void subscribeAllCallbacks() {

        getMultipleFlightsCallback = new IFlightRepository.OnGetMultipleFlightsCallback() {
            @Override
            public void OnSuccess(RealmResults<Flight> flights) {
                adapter.addMoreData(flights);
            }

            @Override
            public void OnError(String message) {
                view.showError(message);
            }
        };

        deleteFlightCallback = new IFlightRepository.OnDeleteFlightCallback() {
            @Override
            public void OnSuccess() {
                view.showSuccess("Successfully deleted!");
            }

            @Override
            public void OnError(String message) {
                view.showError(message);
            }
        };
    }

    @Override
    public void unsubscribeAllCallbacks() {
        getMultipleFlightsCallback = null;
        deleteFlightCallback = null;
    }
}
