package com.aliakseipilko.flightdutytracker.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.aliakseipilko.flightdutytracker.R;
import com.aliakseipilko.flightdutytracker.view.activity.base.BaseActivity;
import com.aliakseipilko.flightdutytracker.view.fragment.EditFlightFragment;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

public class EditFlightActivity extends BaseActivity {

    static long flightId;
    @BindColor(R.color.successColor)
    int successColor;
    @BindColor(R.color.warningColor)
    int warningColor;
    @BindColor(R.color.failureColor)
    int failureColor;
    EditFlightFragment editFlightFragment;
    @BindView(R.id.edit_flight_fragment_container)
    FrameLayout fragmentContainer;
    @BindView(R.id.edit_flight_toolbar)
    Toolbar toolbar;
    @BindView(R.id.edit_toolbar_back)
    ImageView toolbarBack;
    @BindView(R.id.edit_toolbar_check)
    ImageView toolbarCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_flight);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        flightId = getIntent().getLongExtra("flightId", 0);

        editFlightFragment = new EditFlightFragment();
        editFlightFragment.setFlightId(flightId);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.edit_flight_fragment_container, editFlightFragment)
                .commitNow();

        setupToolbar();
    }

    private void setupToolbar() {
        toolbar.setCollapsible(false);
        toolbar.setTitle("Edit Flight");

        toolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditFlightActivity.this, FlightDetailsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.putExtra("flightId", flightId);
                startActivity(intent);
            }
        });

        toolbarCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editFlightFragment.saveFlight();
            }
        });
    }


    @Override
    protected void initComponents() {

    }

    @Override
    public void showSnackbar(String message, boolean isSuccess) {
        Snackbar sb = Snackbar.make(findViewById(R.id.edit_flight_root), message, Snackbar.LENGTH_LONG);

        if (isSuccess) {
            sb.getView().setBackgroundColor(successColor);
        } else {
            sb.getView().setBackgroundColor(failureColor);
        }

        sb.show();
    }
}
