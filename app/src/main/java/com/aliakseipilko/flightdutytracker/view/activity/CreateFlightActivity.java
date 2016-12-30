package com.aliakseipilko.flightdutytracker.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.aliakseipilko.flightdutytracker.R;
import com.aliakseipilko.flightdutytracker.view.activity.base.BaseActivity;
import com.aliakseipilko.flightdutytracker.view.fragment.CreateFlightFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateFlightActivity extends BaseActivity implements CreateFlightFragment.CreateFlightFragmentResultListener {

    @BindView(R.id.create_flight_toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_check)
    ImageView toolbarCheck;
    @BindView(R.id.toolbar_back)
    ImageView toolbarBack;

    CreateFlightFragment createFlightFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_flight);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        setupToolbar();
        createFlightFragment = (CreateFlightFragment) getSupportFragmentManager().findFragmentById(R.id.create_flight_fragment);
        createFlightFragment.setResultListener(this);
    }

    private void setupToolbar() {
        toolbar.setCollapsible(false);
        toolbar.setTitle("Add Flight");

        toolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavUtils.navigateUpFromSameTask(CreateFlightActivity.this);
            }
        });

        toolbarCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createFlightFragment.saveFlight();
            }
        });
    }

    @Override
    protected void initComponents() {

    }

    @Override
    public void onAddFlightComplete() {
        NavUtils.navigateUpFromSameTask(this);
        showToast("Flight added!");
    }

    @Override
    public void onAddFlightFailed(String message) {
        NavUtils.navigateUpFromSameTask(this);
        showToast(message);
    }
}
