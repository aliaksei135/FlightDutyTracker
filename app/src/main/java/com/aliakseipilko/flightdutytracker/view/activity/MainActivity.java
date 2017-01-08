/*
 * Copyright (C) 2017 Aliaksei Pilko
 *
 * Licensed under the Apache License, Version 2.0 (the “License”); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an “AS IS” BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package com.aliakseipilko.flightdutytracker.view.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.aliakseipilko.flightdutytracker.R;
import com.aliakseipilko.flightdutytracker.view.activity.base.BaseActivity;
import com.aliakseipilko.flightdutytracker.view.fragment.FlightFragment;
import com.aliakseipilko.flightdutytracker.view.fragment.HourFragment;
import com.aliakseipilko.flightdutytracker.view.fragment.StatsFragment;
import com.aliakseipilko.flightdutytracker.view.fragment.base.BaseFragment;
import com.mikepenz.aboutlibraries.LibsBuilder;

import org.sufficientlysecure.donations.DonationsFragment;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //Fragment constants
    static final String FRAGMENT_FLIGHT = "FRAGMENT_FLIGHT";
    static final String FRAGMENT_HOURS = "FRAGMENT_HOURS";
    static final String FRAGMENT_STATS = "FRAGMENT_STATS";
    static final String FRAGMENT_DONATE = "FRAGMENT_DONATE";
    static final String FRAGMENT_ABOUT = "FRAGMENT_ABOUT";
    private static final String GOOGLE_PUBKEY = "";//TODO
    private static final String[] GOOGLE_CATALOG = new String[10];
    static String CURRENT_FRAGMENT_TAG;

    @BindColor(R.color.successColor)
    int successColor;
    @BindColor(R.color.warningColor)
    int warningColor;
    @BindColor(R.color.failureColor)
    int failureColor;
    @BindView(R.id.main_toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.main_fragment_container)
    FrameLayout fragmentContainer;

    @Override
    protected void initComponents() {

    }

    @Override
    public void showSnackbar(String message, boolean isSuccess) {
        Snackbar sb = Snackbar.make(findViewById(R.id.root_coord_lo), message, Snackbar.LENGTH_LONG);

        if (isSuccess) {
            sb.getView().setBackgroundColor(successColor);
        } else {
            sb.getView().setBackgroundColor(failureColor);
        }

        sb.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.main_fragment_container);
                if (currentFragment instanceof BaseFragment) {
                    ((BaseFragment) currentFragment).onFABClicked();
                } else {
                    showSnackbar("Not implemented yet!", false);
                }
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (CURRENT_FRAGMENT_TAG == null) {
            CURRENT_FRAGMENT_TAG = FRAGMENT_FLIGHT;
        }
        showCurrentFragment();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("CURRENT_FRAGMENT", CURRENT_FRAGMENT_TAG);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        CURRENT_FRAGMENT_TAG = savedInstanceState.getString("CURRENT_FRAGMENT");
        showCurrentFragment();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (CURRENT_FRAGMENT_TAG.equals(FRAGMENT_DONATE)) {
            //Propagate resusl to fragment to be handled
            Fragment donateFragment = getSupportFragmentManager().findFragmentByTag(FRAGMENT_DONATE);
            donateFragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    void showCurrentFragment() {

        Fragment newFragment = null;

        switch (CURRENT_FRAGMENT_TAG) {
            case FRAGMENT_FLIGHT:
                //Set Fragment to switch
                newFragment = FlightFragment.newInstance();

                fab.show();
                //Change FAB to 'add' icon
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    fab.setImageDrawable(getDrawable(R.drawable.ic_add_white));
                } else {
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_white));
                }
                getSupportActionBar().setTitle("Flights");
                break;
            case FRAGMENT_HOURS:
                newFragment = HourFragment.newInstance();
                fab.hide();
                getSupportActionBar().setTitle("Hours");
                break;
            case FRAGMENT_STATS:
                newFragment = StatsFragment.newInstance();
                fab.hide();
                getSupportActionBar().setTitle("Stats");
                break;
            case FRAGMENT_DONATE:
                newFragment = DonationsFragment.newInstance(true, //TODO Change this for release versions
                        true,
                        GOOGLE_PUBKEY,
                        GOOGLE_CATALOG,
                        getResources().getStringArray(R.array.google_catalog_values),
                        false, null, null, null, //PayPal
                        false, null, null, //Flattr
                        false, null);//BTC
                fab.hide();
                getSupportActionBar().setTitle("Donate");
                break;
            case FRAGMENT_ABOUT:
                newFragment = new LibsBuilder()
                        .withAboutAppName("Flight Duty Tracker")
                        .withAboutIconShown(true)
                        .withAboutVersionShown(true)
                        .withAutoDetect(true)
                        .withSortEnabled(true)
                        .withLibraries("markview", "switchdatetimepicker", "swipeback", "donations")
                        .supportFragment();
                fab.hide();
                getSupportActionBar().setTitle("About");
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_fragment_container, newFragment, CURRENT_FRAGMENT_TAG);
        transaction.commitNow();
        drawer.closeDrawer(GravityCompat.START);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment newFragment = null;

        switch (id) {
            case R.id.nav_flights:

                //Set Fragment to switch
                newFragment = FlightFragment.newInstance();
                CURRENT_FRAGMENT_TAG = FRAGMENT_FLIGHT;

                fab.show();
                //Change FAB to 'add' icon
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    fab.setImageDrawable(getDrawable(R.drawable.ic_add_white));
                } else {
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_white));
                }
                getSupportActionBar().setTitle("Flights");
                break;
            case R.id.nav_hours:
                newFragment = HourFragment.newInstance();
                CURRENT_FRAGMENT_TAG = FRAGMENT_HOURS;
                fab.hide();
                getSupportActionBar().setTitle("Hours");
                break;
            case R.id.nav_statistics:
                newFragment = StatsFragment.newInstance();
                CURRENT_FRAGMENT_TAG = FRAGMENT_STATS;
                fab.hide();
                getSupportActionBar().setTitle("Stats");
                break;
            case R.id.nav_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                overridePendingTransition(R.anim.swipeback_stack_right_in, R.anim.swipeback_stack_to_back);
                return true;
            case R.id.nav_donate:
                newFragment = DonationsFragment.newInstance(true, //TODO Change this for release versions
                        true,
                        GOOGLE_PUBKEY,
                        GOOGLE_CATALOG,
                        getResources().getStringArray(R.array.google_catalog_values),
                        false, null, null, null, //PayPal
                        false, null, null, //Flattr
                        false, null);//BTC
                CURRENT_FRAGMENT_TAG = FRAGMENT_DONATE;
                fab.hide();
                getSupportActionBar().setTitle("Donate");
                break;
            case R.id.nav_about:
                newFragment = new LibsBuilder()
                        .withAboutAppName("Flight Duty Tracker")
                        .withAboutIconShown(true)
                        .withAboutVersionShown(true)
                        .withAutoDetect(true)
                        .withSortEnabled(true)
                        .withLibraries("markview", "switchdatetimepicker", "swipeback", "donations")
                        .supportFragment();
                CURRENT_FRAGMENT_TAG = FRAGMENT_ABOUT;
                fab.hide();
                getSupportActionBar().setTitle("About");
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_fragment_container, newFragment, CURRENT_FRAGMENT_TAG);
        transaction.commitNow();
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
