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

import com.aliakseipilko.flightdutytracker.presenter.IFlightDetailsPresenter;
import com.aliakseipilko.flightdutytracker.realm.model.Flight;
import com.aliakseipilko.flightdutytracker.realm.repository.IFlightRepository;
import com.aliakseipilko.flightdutytracker.realm.repository.impl.FlightRepository;
import com.aliakseipilko.flightdutytracker.view.fragment.FlightDetailsFragment;

public class FlightDetailsPresenter implements IFlightDetailsPresenter {

    private IFlightRepository.OnGetSingleFlightCallback getSingleFlightCallback;
    private IFlightRepository.OnDeleteFlightCallback deleteFlightCallback;

    private FlightRepository repository;

    private FlightDetailsFragment view;

    public FlightDetailsPresenter(FlightDetailsFragment view) {
        this.view = view;

        //TODO inject this with Dagger
        repository = new FlightRepository();
    }

    @Override
    public void getSingleFlightById(long id) {
        repository.getSingleFlightById(id, getSingleFlightCallback);
    }

    @Override
    public void deleteFlightById(long id) {
        repository.deleteFlightById(id, deleteFlightCallback);
    }

    @Override
    public void subscribeAllCallbacks() {
        getSingleFlightCallback = new IFlightRepository.OnGetSingleFlightCallback() {
            @Override
            public void OnSuccess(Flight flight) {
                view.setFlight(flight);
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
        getSingleFlightCallback = null;
        deleteFlightCallback = null;
    }
}
