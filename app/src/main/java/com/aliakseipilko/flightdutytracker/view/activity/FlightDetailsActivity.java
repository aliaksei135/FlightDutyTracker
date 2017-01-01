package com.aliakseipilko.flightdutytracker.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

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
        setSupportActionBar(toolbar);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSnackbar("Not implemented yet", false);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        super.onBackPressed();
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
        getSupportActionBar().setTitle(title);
    }


}