package com.aliakseipilko.flightdutytracker.view.fragment.hoursFragments.base;

import android.support.v4.app.Fragment;

public abstract class BaseHourFragment extends Fragment {

    public abstract void showDutyHours(String s, double dutyMillis);

    public abstract void showFlightHours(String s, double flightMillis);

    public abstract void showError(String message);
}
