package com.aliakseipilko.flightdutytracker.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.aliakseipilko.flightdutytracker.view.fragment.hoursFragments.CalendarYearHourFragment;
import com.aliakseipilko.flightdutytracker.view.fragment.hoursFragments.SevenDaysHourFragment;
import com.aliakseipilko.flightdutytracker.view.fragment.hoursFragments.TwentyEightDaysHourFragment;


public class HoursAdapter extends FragmentPagerAdapter {

    public HoursAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return SevenDaysHourFragment.newInstance();
            case 1:
                return TwentyEightDaysHourFragment.newInstance();
            case 2:
                return CalendarYearHourFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 0:
                return "7 Days";
            case 1:
                return "28 Days";
            case 2:
                return "Calendar Year";
        }
        return null;
    }
}
