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
