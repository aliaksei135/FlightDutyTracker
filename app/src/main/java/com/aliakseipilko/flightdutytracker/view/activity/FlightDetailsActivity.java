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
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.aliakseipilko.flightdutytracker.R;
import com.aliakseipilko.flightdutytracker.view.activity.base.BaseActivity;
import com.aliakseipilko.flightdutytracker.view.fragment.FlightDetailsFragment;
import com.hannesdorfmann.swipeback.Position;
import com.hannesdorfmann.swipeback.SwipeBack;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FlightDetailsActivity extends BaseActivity implements FlightDetailsFragment.FlightDetailsFragmentInterface {

    @BindColor(R.color.successColor)
    int successColor;
    @BindColor(R.color.warningColor)
    int warningColor;
    @BindColor(R.color.failureColor)
    int failureColor;

    @BindView(R.id.flight_details_fab)
    FloatingActionButton fab;
    @BindView(R.id.details_toolbar)
    Toolbar toolbar;
    @BindView(R.id.details_toolbar_back)
    ImageView toolbarBack;
    @BindView(R.id.details_toolbar_bin)
    ImageView toolbarBin;
    @BindView(R.id.details_toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.flight_details_fragment_container)
    FrameLayout fragmentContainer;

    private FlightDetailsFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SwipeBack.attach(this, Position.LEFT)
                .setContentView(R.layout.activity_flight_details)
                .setSwipeBackView(R.layout.swipeback_default);
        ButterKnife.bind(this);

        toolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        toolbarBin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment.deleteFlight();
                onBackPressed();
            }
        });

        setSupportActionBar(toolbar);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((FlightDetailsFragment) getSupportFragmentManager().findFragmentByTag("FLIGHT_DETAILS")).onFABClicked();
            }
        });


        setupFragment(getIntent());
    }

    private void setupFragment(Intent intent) {
        fragment = FlightDetailsFragment.newInstance(intent.getLongExtra("flightId", 1));
        fragment.setInterface(this);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.flight_details_fragment_container, fragment, "FLIGHT_DETAILS");
        ft.commitNow();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(FlightDetailsActivity.this, MainActivity.class));
        overridePendingTransition(R.anim.swipeback_stack_to_front,
                R.anim.swipeback_stack_right_out);
    }

    @Override
    protected void initComponents() {

    }

    @Override
    public void showSnackbar(String message, boolean isSuccess) {
        Snackbar sb = Snackbar.make(findViewById(R.id.flight_details_root), message, Snackbar.LENGTH_LONG);

        if (isSuccess) {
            sb.getView().setBackgroundColor(successColor);
        } else {
            sb.getView().setBackgroundColor(failureColor);
        }

        sb.show();
    }

    @Override
    public void setToolbarTitle(String title) {
        toolbarTitle.setText(title);
    }

}
