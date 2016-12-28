package com.aliakseipilko.flightdutytracker.presenter.impl;

import com.aliakseipilko.flightdutytracker.presenter.ICreateFlightPresenter;
import com.aliakseipilko.flightdutytracker.realm.model.Flight;
import com.aliakseipilko.flightdutytracker.realm.repository.IFlightRepository;
import com.aliakseipilko.flightdutytracker.realm.repository.impl.FlightRepository;
import com.aliakseipilko.flightdutytracker.view.fragment.base.BaseFragment;

import java.util.Date;

public class CreateFlightPresenter implements ICreateFlightPresenter {

    private IFlightRepository.OnAddFlightCallback addFlightCallback;

    private FlightRepository repository;

    //TODO Change this
    private BaseFragment view;

    public CreateFlightPresenter(BaseFragment view) {
        this.view = view;
        repository = new FlightRepository();
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
    public void subscribeAllCallbacks() {
        addFlightCallback = new IFlightRepository.OnAddFlightCallback() {
            @Override
            public void OnSuccess() {
                view.showSuccess("Successfully Added!");
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
    }
}
