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

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;

import com.aliakseipilko.flightdutytracker.presenter.ICreateFlightPresenter;
import com.aliakseipilko.flightdutytracker.realm.model.Flight;
import com.aliakseipilko.flightdutytracker.realm.repository.IFlightRepository;
import com.aliakseipilko.flightdutytracker.realm.repository.impl.FlightRepository;
import com.aliakseipilko.flightdutytracker.utils.AirportCode;
import com.aliakseipilko.flightdutytracker.utils.AirportCodeConverter;
import com.aliakseipilko.flightdutytracker.view.fragment.CreateFlightFragment;

import java.util.Date;

public class CreateFlightPresenter implements ICreateFlightPresenter {

    private IFlightRepository.OnAddFlightCallback addFlightCallback;

    private FlightRepository repository;

    private CreateFlightFragment view;

    private AirportCodeConverter codeConverter;

    public CreateFlightPresenter(CreateFlightFragment view) {
        this.view = view;
        repository = new FlightRepository();
        //This should be injected with Dagger, but its retarded
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.IDENTITY);
        codeConverter = new AirportCodeConverter(view.getContext().getApplicationContext(), gsonBuilder.create());
    }

    @Override
    public void addFlight(Flight flight) {
        repository.addFlight(flight, addFlightCallback);
    }

    @Override
    public void addFlight(String departureIATACode, String departureICAOCode, String arrivalIATACode, String arrivalICAOCode, Date startDutyTime, Date startFlightTime, Date endFlightTime, Date endDutyTime, String acType, String flightNumber) {
        Flight flight = new Flight();

        flight.setId(repository.getNextID());
        flight.setDepartureIATACode(departureIATACode);
        flight.setDepartureICAOCode(departureICAOCode);
        flight.setArrivalIATACode(arrivalIATACode);
        flight.setArrivalICAOCode(arrivalICAOCode);
        flight.setStartDutyTime(startDutyTime);
        flight.setStartFlightTime(startFlightTime);
        flight.setEndFlightTime(endFlightTime);
        flight.setEndDutyTime(endDutyTime);
        flight.setAcType(acType);
        flight.setFlightNumber(flightNumber);

        repository.addFlight(flight, addFlightCallback);
    }

    @Override
    public void addFlight(String departureCode, String arrivalCode, Date startDutyTime, Date startFlightTime, Date endFlightTime, Date endDutyTime, String acType, String flightNumber) {
        Flight flight = new Flight();

        AirportCode.CODE_TYPES codetype = determineAirportCodeType(departureCode, arrivalCode);

        if (codetype == null) {
            addFlightCallback.OnError("Airport Codes are invalid or do not match!");
            return;
        }
        if (codetype == AirportCode.CODE_TYPES.IATA_CODE) {
            flight.setDepartureIATACode(departureCode);
            flight.setArrivalIATACode(arrivalCode);
            flight.setDepartureICAOCode(codeConverter.convertIATAtoICAO(departureCode));
            flight.setArrivalICAOCode(codeConverter.convertIATAtoICAO(arrivalCode));
        } else {
            flight.setDepartureICAOCode(departureCode);
            flight.setArrivalICAOCode(arrivalCode);
            flight.setDepartureIATACode(codeConverter.convertICAOtoIATA(departureCode));
            flight.setArrivalIATACode(codeConverter.convertICAOtoIATA(arrivalCode));
        }

        flight.setId(repository.getNextID());
        flight.setStartDutyTime(startDutyTime);
        flight.setStartFlightTime(startFlightTime);
        flight.setEndFlightTime(endFlightTime);
        flight.setEndDutyTime(endDutyTime);
        flight.setAcType(acType);
        flight.setFlightNumber(flightNumber);

        repository.addFlight(flight, addFlightCallback);
    }

    @Override
    public AirportCode.CODE_TYPES determineAirportCodeType(String departureCode, String arrivalCode) {
        int depCodeLength = departureCode.trim().length();
        int arrCodeLength = arrivalCode.trim().length();

        //IATA Codes are always 3 chars long
        if (depCodeLength == 3) {
            return AirportCode.CODE_TYPES.IATA_CODE;
        }
        //ICAO Codes are always 4 chars long
        if (depCodeLength == 4) {
            return AirportCode.CODE_TYPES.ICAO_CODE;
        }
        //Neither type
        return null;

    }

    @Override
    public void subscribeAllCallbacks() {
        addFlightCallback = new IFlightRepository.OnAddFlightCallback() {
            @Override
            public void OnSuccess() {
                CreateFlightFragment.callback.onAddFlightComplete();
            }

            @Override
            public void OnError(String message) {
                CreateFlightFragment.callback.onAddFlightFailed(message);
            }
        };
    }

    @Override
    public void unsubscribeAllCallbacks() {
        addFlightCallback = null;
    }
}
