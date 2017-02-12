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

import android.content.SharedPreferences;
import android.os.Environment;

import com.aliakseipilko.flightdutytracker.presenter.IBackupPresenter;
import com.aliakseipilko.flightdutytracker.realm.model.Flight;
import com.aliakseipilko.flightdutytracker.realm.repository.IFlightRepository;
import com.aliakseipilko.flightdutytracker.realm.repository.impl.FlightRepository;
import com.aliakseipilko.flightdutytracker.utils.BackupUtils;
import com.aliakseipilko.flightdutytracker.view.fragment.backupRestoreFragments.base.BackupRestoreBaseFragment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.RealmResults;


public class BackupPresenter implements IBackupPresenter {

    IFlightRepository.OnGetMultipleFlightsCallback getMultipleFlightsCallback;

    FlightRepository repository;

    BackupRestoreBaseFragment view;

    SharedPreferences prefs = view.getContext().getApplicationContext().getSharedPreferences("prefs", 0);

    public BackupPresenter(BackupRestoreBaseFragment view) {

        this.view = view;
        repository = new FlightRepository();
    }

    @Override
    public void backupAllFlights() {
        repository.getAllFlights(getMultipleFlightsCallback);
    }

    @Override
    public void restoreFlights(File srcFile) {
        repository.restoreFlightsByFile(srcFile);
    }

    @Override
    public String getLatestBackupDate() {
        return prefs.getString("backupDate", "No Backups made");
    }

    @Override
    public void setLatestBackupDate() {
        view.setBackupDate(prefs.getString("backupDate", "No Backups made"));
    }

    @Override
    public void subscribeAllCallbacks() {
        getMultipleFlightsCallback = new IFlightRepository.OnGetMultipleFlightsCallback() {
            @Override
            public void OnSuccess(RealmResults<Flight> flights) {
                File destFile = new File(Environment.getExternalStorageDirectory().getPath() + "/flights_backup.json");
                try {
                    BackupUtils.serialiseFlightsRealmToFile(destFile, flights);

                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YY");
                    prefs.edit().putString("backupDate", sdf.format(new Date())).commit();
                    setLatestBackupDate();
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
