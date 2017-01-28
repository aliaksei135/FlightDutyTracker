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

import android.os.Environment;
import android.view.View;

import com.aliakseipilko.flightdutytracker.presenter.IBackupPresenter;
import com.aliakseipilko.flightdutytracker.realm.model.Flight;
import com.aliakseipilko.flightdutytracker.realm.repository.IFlightRepository;
import com.aliakseipilko.flightdutytracker.realm.repository.impl.FlightRepository;
import com.aliakseipilko.flightdutytracker.utils.BackupUtils;

import java.io.File;
import java.io.IOException;

import io.realm.Realm;
import io.realm.RealmResults;


public class BackupPresenter implements IBackupPresenter {

    IFlightRepository.OnGetMultipleFlightsCallback getMultipleFlightsCallback;

    FlightRepository repository;

    View view;

    public BackupPresenter(View view) {

        this.view = view;
        repository = new FlightRepository();
    }

    @Override
    public void backupAllFlights() {
        repository.getAllFlights(getMultipleFlightsCallback);
    }

    @Override
    public void restoreFlights(File srcFile) {
        BackupUtils.deserialiseFlightsFileToRealm(Realm.getDefaultInstance(), srcFile);
    }

    @Override
    public void subscribeAllCallbacks() {
        getMultipleFlightsCallback = new IFlightRepository.OnGetMultipleFlightsCallback() {
            @Override
            public void OnSuccess(RealmResults<Flight> flights) {
                File destFile = new File(Environment.getExternalStorageDirectory().getPath() + "/flights_backup.json");
                try {
                    BackupUtils.serialiseFlightsRealmToFile(destFile, flights);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void OnError(String message) {
                //TODO Handle
            }
        };
    }

    @Override
    public void unsubscribeAllCallbacks() {
        getMultipleFlightsCallback = null;
    }
}
