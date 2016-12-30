package com.aliakseipilko.flightdutytracker.view.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aliakseipilko.flightdutytracker.R;
import com.aliakseipilko.flightdutytracker.presenter.impl.FlightDetailsPresenter;
import com.aliakseipilko.flightdutytracker.realm.model.Flight;
import com.aliakseipilko.flightdutytracker.view.activity.FlightDetailsActivity;
import com.aliakseipilko.flightdutytracker.view.fragment.base.BaseFragment;

import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FlightDetailsFragment extends BaseFragment {

    private static long flightId;
    @BindView(R.id.detail_departure_et)
    TextView departureTv;
    @BindView(R.id.detail_arrival_et)
    TextView arrivalTv;
    @BindView(R.id.detail_duty_from_date_tv)
    TextView dutyFromTv;
    @BindView(R.id.detail_duty_to_date_tv)
    TextView dutyToTv;
    @BindView(R.id.detail_flight_from_date_tv)
    TextView flightFromTv;
    @BindView(R.id.detail_flight_to_date_tv)
    TextView flightToTv;
    private FlightDetailsPresenter presenter;
    private Unbinder unbinder;
    private Flight flight;
    private FlightDetailsActivity activityCallback;

    public FlightDetailsFragment() {
        presenter = new FlightDetailsPresenter(this);
    }

    public static FlightDetailsFragment newInstance(long flightId) {
        FlightDetailsFragment.flightId = flightId;
        return new FlightDetailsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_flight_details, container, false);
        unbinder = ButterKnife.bind(this, view);

        presenter.subscribeAllCallbacks();

        presenter.getSingleFlightById(flightId);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void displayFlight() {

        activityCallback.setToolbarTitle(flight.getFlightNumber());

        if (flight.getDepartureIATACode() == null) {
            departureTv.setText(flight.getDepartureICAOCode());
            arrivalTv.setText(flight.getArrivalICAOCode());
        } else {
            departureTv.setText(flight.getDepartureIATACode());
            arrivalTv.setText(flight.getArrivalIATACode());
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm", Locale.getDefault());
        dutyFromTv.setText(sdf.format(flight.getStartDutyTime()));
        dutyToTv.setText(sdf.format(flight.getEndDutyTime()));
        flightFromTv.setText(sdf.format(flight.getStartFlightTime()));
        flightToTv.setText(sdf.format(flight.getEndFlightTime()));
    }

    @Override
    public void onFABClicked() {

    }

    public void setFlight(Flight flight) {
        this.flight = flight;
        displayFlight();
    }

    public void setInterface(FlightDetailsActivity activityCallback) {
        this.activityCallback = activityCallback;
    }

    public interface FlightDetailsFragmentInterface {
        void setToolbarTitle(String title);
    }

}
