/*
 * Copyright (C) 2017 Aliaksei Pilko
 *
 * Licensed under the Apache License, Version 2.0 (the “License”); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an “AS IS” BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package com.aliakseipilko.flightdutytracker.realm.repository.impl;

import android.support.annotation.NonNull;

import com.aliakseipilko.flightdutytracker.dagger.components.DaggerRepositoryComponent;
import com.aliakseipilko.flightdutytracker.dagger.components.RepositoryComponent;
import com.aliakseipilko.flightdutytracker.dagger.modules.RealmModule;
import com.aliakseipilko.flightdutytracker.realm.model.Flight;
import com.aliakseipilko.flightdutytracker.realm.repository.IFlightRepository;
import com.aliakseipilko.flightdutytracker.utils.AirportCode;
import com.aliakseipilko.flightdutytracker.utils.BackupUtils;
import com.aliakseipilko.flightdutytracker.view.fragment.backupRestoreFragments.base.BackupRestoreBaseFragment;

import java.io.File;
import java.util.Date;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

import static com.aliakseipilko.flightdutytracker.utils.AirportCode.CODE_TYPES.ICAO_CODE;

public class FlightRepository implements IFlightRepository {

    @Inject
    Realm realm;
    private RepositoryComponent component;

    public FlightRepository() {
        component = DaggerRepositoryComponent.builder().realmModule(new RealmModule()).build();
        component.inject(this);
    }

    @Override
    public void addFlight(final Flight flight, @NonNull final OnAddFlightCallback callback) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(flight);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                callback.OnSuccess();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                callback.OnError(error.getMessage());
            }
        });
    }

    @Override
    public void restoreFlightsByFile(File srcFile, BackupRestoreBaseFragment baseFragment) {
        BackupUtils.deserialiseFlightsFileToRealm(realm, srcFile, baseFragment);
    }

    @Override
    public void editFlight(final Flight flight, @NonNull final OnAddFlightCallback callback) {

        realm.beginTransaction();
        realm.copyToRealmOrUpdate(flight);
        realm.commitTransaction();
        //TODO Work this out
        callback.OnSuccess();

/*        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(flight);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                callback.OnSuccess();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                callback.OnError(error.getMessage());
            }
        });*/
    }

    @Override
    public void deleteFlight(final Flight flight, @NonNull final OnDeleteFlightCallback callback) {
        //Find the flight by Primary key ID field first
        final RealmResults<Flight> results = realm.where(Flight.class)
                .equalTo("id", flight.getId())
                .findAll();

        realm.beginTransaction();
        results.deleteAllFromRealm();
        realm.commitTransaction();
        if (results.isEmpty()) {
            callback.OnSuccess();
        } else {
            callback.OnError("That didn't work");
        }

/*        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                //ID is unique, so results should contain at most 1 match, hence deleting all would be same as delete first/last/an index
                results.deleteAllFromRealm();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                callback.OnSuccess();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                callback.OnError(error.getMessage());
            }
        });*/
    }

    @Override
    public void deleteFlightByFlightNumber(String flightNumber, @NonNull final OnDeleteFlightCallback callback) {
        final RealmResults<Flight> results = realm.where(Flight.class)
                .equalTo("flightNumber", flightNumber)
                .findAll();

        realm.beginTransaction();
        results.deleteAllFromRealm();
        realm.commitTransaction();
        if (results.isEmpty()) {
            callback.OnSuccess();
        } else {
            callback.OnError("That didn't work");
        }

/*        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                //ID is unique, so results should contain at most 1 match, hence deleting all would be same as delete first/last/an index
                results.deleteAllFromRealm();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                callback.OnSuccess();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                callback.OnError(error.getMessage());
            }
        });*/
    }

    @Override
    public void deleteFlightById(long id, @NonNull final OnDeleteFlightCallback callback) {
        final RealmResults<Flight> results = realm.where(Flight.class)
                .equalTo("id", id)
                .findAll();

        realm.beginTransaction();
        results.deleteAllFromRealm();
        realm.commitTransaction();
        if (results.isEmpty()) {
            callback.OnSuccess();
        } else {
            callback.OnError("That didn't work");
        }

        /*realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                //ID is unique, so results should contain at most 1 match, hence deleting all would be same as delete first/last/an index
                results.deleteAllFromRealm();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                callback.OnSuccess();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                callback.OnError(error.getMessage());
            }
        });*/
    }

    @Override
    public void getSingleFlightById(long id, @NonNull OnGetSingleFlightCallback callback) {
        Flight result = realm.where(Flight.class)
                .equalTo("id", id)
                .findFirst();

        if (result.isLoaded()) {
            callback.OnSuccess(result);
        } else {
            callback.OnError("Flight not loaded");
        }
    }

    @Override
    public void getMultipleFlightsByFlightNumber(String flightNumber, @NonNull OnGetMultipleFlightsCallback callback) {
        RealmResults<Flight> results = realm.where(Flight.class)
                .equalTo("flightNumber", flightNumber)
                .findAll();

        if (results.isLoaded()) {
            callback.OnSuccess(results);
        } else {
            callback.OnError("Flights not loaded");
        }
    }

    @Override
    public void getMultipleFlightsByACType(String ACType, @NonNull OnGetMultipleFlightsCallback callback) {
        RealmResults<Flight> results = realm.where(Flight.class)
                .equalTo("acType", ACType)
                .findAll();

        if (results.isLoaded()) {
            callback.OnSuccess(results);
        } else {
            callback.OnError("Flights not loaded");
        }
    }

    @Override
    public void getMultipleFlightsByAirportCode(AirportCode.CODE_TYPES codeType, AirportCode.CODE_PLACES codePlace, String airportCode, @NonNull OnGetMultipleFlightsCallback callback) {

        RealmResults<Flight> results;

        if (codePlace == null) {
            if (codeType == ICAO_CODE) {
                results = realm.where(Flight.class)
                        .equalTo("departureICAOCode", airportCode)
                        .or()
                        .equalTo("arrivalICAOCode", airportCode)
                        .findAll();
            } else {
                results = realm.where(Flight.class)
                        .equalTo("departureIATACode", airportCode)
                        .or()
                        .equalTo("arrivalIATACode", airportCode)
                        .findAll();
            }
        } else if (codeType == null) {
            if (codePlace == AirportCode.CODE_PLACES.DEPARTURE_CODE) {
                results = realm.where(Flight.class)
                        .equalTo("departureICAOCode", airportCode)
                        .or()
                        .equalTo("departureIATACode", airportCode)
                        .findAll();
            } else {
                results = realm.where(Flight.class)
                        .equalTo("arrivalICAOCode", airportCode)
                        .or()
                        .equalTo("arrivalIATACode", airportCode)
                        .findAll();
            }
        } else {
            if (codeType == ICAO_CODE) {
                if (codePlace == AirportCode.CODE_PLACES.DEPARTURE_CODE) {
                    results = realm.where(Flight.class)
                            .equalTo("departureICAOCode", airportCode)
                            .findAll();
                } else {
                    results = realm.where(Flight.class)
                            .equalTo("arrivalICAOCode", airportCode)
                            .findAll();
                }
            } else {
                if (codePlace == AirportCode.CODE_PLACES.DEPARTURE_CODE) {
                    results = realm.where(Flight.class)
                            .equalTo("departureIATACode", airportCode)
                            .findAll();
                } else {
                    results = realm.where(Flight.class)
                            .equalTo("arrivalIATACode", airportCode)
                            .findAll();
                }
            }
        }

        if (results.isLoaded()) {
            callback.OnSuccess(results);
        } else {
            callback.OnError("Flights not loaded");
        }
    }

    @Override
    public void getAllFlights(@NonNull OnGetMultipleFlightsCallback callback) {
        RealmResults<Flight> results = realm.where(Flight.class).findAll();

        if (results.isLoaded()) {
            callback.OnSuccess(results);
        } else {
            callback.OnError("Flights not loaded");
        }
    }

    @Override
    public void getMultipleFlightsByIdCount(long startId, int count, Sort sort, @NonNull OnGetMultipleFlightsCallback callback) {
        RealmResults<Flight> results = realm.where(Flight.class)
                .between("id", startId + 1, startId + count + 1) //Inverted order
                .findAllSorted("startDutyTime", sort);

        if (results.isLoaded()) {
            callback.OnSuccess(results);
        } else {
            callback.OnError("Flights not loaded");
        }
    }

    @Override
    public void getMultipleFlightsByDateRange(Date startDate, Date endDate, Sort sort, @NonNull OnGetMultipleFlightsCallback callback) {
        RealmResults<Flight> results = realm.where(Flight.class)
                .between("startDutyTime", startDate, endDate)
                .findAllSorted("startDutyTime", sort);

        if (results.isLoaded()) {
            callback.OnSuccess(results);
        } else {
            callback.OnError("Flights not loaded");
        }
    }

    @Override
    public long getNextID() {
        Number idNum = realm.where(Flight.class).max("id");
        if (idNum == null) {
            return 1;
        } else {
            long id = idNum.longValue();
            return id + 1;
        }
    }
}
