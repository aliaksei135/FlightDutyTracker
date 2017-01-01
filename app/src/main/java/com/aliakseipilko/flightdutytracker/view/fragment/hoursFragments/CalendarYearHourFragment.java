package com.aliakseipilko.flightdutytracker.view.fragment.hoursFragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aliakseipilko.flightdutytracker.R;
import com.aliakseipilko.flightdutytracker.dagger.components.DaggerStorageComponent;
import com.aliakseipilko.flightdutytracker.dagger.components.StorageComponent;
import com.aliakseipilko.flightdutytracker.dagger.modules.PrefsModule;
import com.aliakseipilko.flightdutytracker.presenter.impl.HourPresenter;
import com.aliakseipilko.flightdutytracker.view.fragment.HourFragment;
import com.aliakseipilko.flightdutytracker.view.fragment.hoursFragments.base.BaseHourFragment;
import com.aliakseipilko.flightdutytracker.view.views.MarkView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CalendarYearHourFragment extends BaseHourFragment {

    @BindView(R.id.year_duty_hours)
    MarkView dutyHoursMarkView;
    @BindView(R.id.year_flight_hours)
    MarkView flightHoursMarkView;

    Unbinder unbinder;
    StorageComponent storageComponent;
    @Inject
    SharedPreferences prefs;
    long maxDutyMillis, maxFlightMillis;
    private HourPresenter presenter;

    public CalendarYearHourFragment() {
        // Required empty public constructor
    }


    public static CalendarYearHourFragment newInstance() {

        return new CalendarYearHourFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar_year_hour, container, false);
        unbinder = ButterKnife.bind(this, view);

        presenter = new HourPresenter(this);
        presenter.subscribeAllCallbacks();
        //TODO Fix this for leap years
        presenter.getFlightHoursInPastDays(365);
        presenter.getDutyHoursInPastDays(365);

        storageComponent = DaggerStorageComponent.builder().prefsModule(new PrefsModule(getContext().getApplicationContext())).build();
        storageComponent.inject(this);

        maxDutyMillis = prefs.getInt("maxYearDutyHours", 60) * 60 * 60 * 1000;
        maxFlightMillis = prefs.getInt("maxYearFlightHours", 60) * 60 * 60 * 1000;

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showDutyHours(String s, long dutyMillis) {
        dutyHoursMarkView.setMax((int) maxDutyMillis);
        dutyHoursMarkView.setMark((int) dutyMillis);
        dutyHoursMarkView.setText(s);
    }

    @Override
    public void showFlightHours(String s, long flightMillis) {
        flightHoursMarkView.setMax((int) maxFlightMillis);
        flightHoursMarkView.setMark((int) flightMillis);
        flightHoursMarkView.setText(s);
    }

    @Override
    public void showError(String message) {
        ((HourFragment) getParentFragment()).showError(message);
    }
}