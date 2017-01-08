package com.aliakseipilko.flightdutytracker.presenter.impl;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;

import com.aliakseipilko.flightdutytracker.presenter.IEditFlightPresenter;
import com.aliakseipilko.flightdutytracker.realm.model.Flight;
import com.aliakseipilko.flightdutytracker.realm.repository.IFlightRepository;
import com.aliakseipilko.flightdutytracker.realm.repository.impl.FlightRepository;
import com.aliakseipilko.flightdutytracker.utils.AirportCode;
import com.aliakseipilko.flightdutytracker.utils.AirportCodeConverter;
import com.aliakseipilko.flightdutytracker.view.fragment.EditFlightFragment;

import java.util.Date;


public class EditFlightPresenter implements IEditFlightPresenter {

    IFlightRepository.OnAddFlightCallback addFlightCallback;
    IFlightRepository.OnGetSingleFlightCallback getSingleFlightCallback;

    EditFlightFragment view;
    FlightRepository repository;
    AirportCodeConverter codeConverter;

    public EditFlightPresenter(EditFlightFragment view) {
        this.view = view;
        repository = new FlightRepository();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.IDENTITY);
        codeConverter = new AirportCodeConverter(view.getContext().getApplicationContext(), gsonBuilder.create());
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
    public void editFlight(long id, String departureCode, String arrivalCode, Date startDutyTime, Date startFlightTime, Date endFlightTime, Date endDutyTime, String acType, String flightNumber) {
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

        flight.setId(id);
        flight.setStartDutyTime(startDutyTime);
        flight.setStartFlightTime(startFlightTime);
        flight.setEndFlightTime(endFlightTime);
        flight.setEndDutyTime(endDutyTime);
        flight.setAcType(acType);
        flight.setFlightNumber(flightNumber);

        repository.editFlight(flight, addFlightCallback);
    }

    @Override
    public void getSingleFlightById(long id) {
        repository.getSingleFlightById(id, getSingleFlightCallback);
    }

    @Override
    public void subscribeAllCallbacks() {
        addFlightCallback = new IFlightRepository.OnAddFlightCallback() {
            @Override
            public void OnSuccess() {
                view.showSuccess("Flight modified!");
            }

            @Override
            public void OnError(String message) {
                view.showError(message);
            }
        };
        getSingleFlightCallback = new IFlightRepository.OnGetSingleFlightCallback() {
            @Override
            public void OnSuccess(Flight flight) {
                view.setEditFlight(flight);
            }

            @Override
            public void OnError(String message) {
                view.showError(message);
            }
        };
    }

    @Override
    public void unsubscribeAllCallbacks() {
        addFlightCallback = null;
        getSingleFlightCallback = null;
    }
}
