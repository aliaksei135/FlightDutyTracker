/*
 * Copyright (C) 2017 Aliaksei Pilko
 *
 * Licensed under the Apache License, Version 2.0 (the “License”); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an “AS IS” BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package com.aliakseipilko.flightdutytracker.realm.repository;

import android.support.annotation.NonNull;

import com.aliakseipilko.flightdutytracker.realm.model.Flight;
import com.aliakseipilko.flightdutytracker.utils.AirportCode;
import com.aliakseipilko.flightdutytracker.view.fragment.backupRestoreFragments.base.BackupRestoreBaseFragment;

import java.io.File;
import java.util.Date;

import io.realm.RealmResults;
import io.realm.Sort;

public interface IFlightRepository extends IBaseRepository {

    void addFlight(Flight flight, @NonNull OnAddFlightCallback callback);

    void restoreFlightsByFile(File srcFile, BackupRestoreBaseFragment baseFragment);

    void editFlight(Flight flight, @NonNull OnAddFlightCallback callback);

    void deleteFlight(Flight flight, @NonNull OnDeleteFlightCallback callback);

    void deleteFlightByFlightNumber(String flightNumber, @NonNull OnDeleteFlightCallback callback);

    void deleteFlightById(long id, @NonNull OnDeleteFlightCallback callback);

    void getSingleFlightById(long id, @NonNull OnGetSingleFlightCallback callback);

    void getMultipleFlightsByFlightNumber(String flightNumber, @NonNull OnGetMultipleFlightsCallback callback);

    void getMultipleFlightsByACType(String ACType, @NonNull OnGetMultipleFlightsCallback callback);

    void getMultipleFlightsByAirportCode(AirportCode.CODE_TYPES codeType, AirportCode.CODE_PLACES codePlace, String airportCode, @NonNull OnGetMultipleFlightsCallback callback);

    void getAllFlights(@NonNull OnGetMultipleFlightsCallback callback);

    void getMultipleFlightsByIdCount(long startId, int count, Sort sort, @NonNull OnGetMultipleFlightsCallback callback);

    void getMultipleFlightsByDateRange(Date startDate, Date endDate, Sort sort, @NonNull OnGetMultipleFlightsCallback callback);

    interface OnAddFlightCallback {
        void OnSuccess();

        void OnError(String message);
    }

    interface OnGetMultipleFlightsCallback {
        void OnSuccess(RealmResults<Flight> flights);

        void OnError(String message);
    }

    interface OnGetSingleFlightCallback {
        void OnSuccess(Flight flight);

        void OnError(String message);
    }

    interface OnDeleteFlightCallback {
        void OnSuccess();

        void OnError(String message);
    }
}
