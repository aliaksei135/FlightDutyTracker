/*
 * Copyright (C) 2017 Aliaksei Pilko
 *
 * Licensed under the Apache License, Version 2.0 (the “License”); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an “AS IS” BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

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
