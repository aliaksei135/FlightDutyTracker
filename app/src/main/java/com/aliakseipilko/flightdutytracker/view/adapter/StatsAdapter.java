package com.aliakseipilko.flightdutytracker.view.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.aliakseipilko.flightdutytracker.view.fragment.hoursFragments.CalendarYearHourFragment;
import com.aliakseipilko.flightdutytracker.view.fragment.statsFragments.SevenDaysStatsFragment;
import com.aliakseipilko.flightdutytracker.view.fragment.statsFragments.TwentyEightDaysStatsFragment;

public class StatsAdapter extends FragmentPagerAdapter {

    public StatsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return SevenDaysStatsFragment.newInstance();
            case 1:
                return TwentyEightDaysStatsFragment.newInstance();
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
