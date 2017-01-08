/*
 * Copyright (C) 2017 Aliaksei Pilko
 *
 * Licensed under the Apache License, Version 2.0 (the “License”); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an “AS IS” BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package com.aliakseipilko.flightdutytracker.presenter;

import com.aliakseipilko.flightdutytracker.realm.model.Flight;
import com.aliakseipilko.flightdutytracker.utils.AirportCode;

import java.util.Date;

public interface IFlightPresenter extends IBasePresenter {

    void deleteFlightById(long id);

    void deleteFlight(Flight flight);

    void deleteFlightByFlightNumber(String flightNumber);

    void getMultipleFlightsByFlightNumber(String flightNumber);

    void getMultipleFlightsByACType(String ACType);

    void getMultipleFlightsByAirportCode(AirportCode.CODE_TYPES codeType, AirportCode.CODE_PLACES codePlace, String airportCode);

    void getAllFlights();

    void getMultipleFlightsByIdCount(long startId, int count);

    void getMultipleFlightsByDateRange(Date startDate, Date endDate);
}
