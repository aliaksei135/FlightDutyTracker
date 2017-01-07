package com.aliakseipilko.flightdutytracker.view.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;

import com.aliakseipilko.flightdutytracker.R;
import com.aliakseipilko.flightdutytracker.view.activity.base.BaseActivity;
import com.aliakseipilko.flightdutytracker.view.fragment.SettingsFragment;
import com.hannesdorfmann.swipeback.SwipeBack;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsActivity extends BaseActivity {

    @BindColor(R.color.successColor)
    int successColor;
    @BindColor(R.color.failureColor)
    int failureColor;

    @BindView(R.id.settings_toolbar)
    Toolbar toolbar;

    @Override
    protected void initComponents() {

    }

    @Override
    public void showSnackbar(String message, boolean isSuccess) {
        Snackbar sb = Snackbar.make(findViewById(R.id.settings_root), message, Snackbar.LENGTH_LONG);

        if (isSuccess) {
            sb.getView().setBackgroundColor(successColor);
        } else {
            sb.getView().setBackgroundColor(failureColor);
        }

        sb.show();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SwipeBack.attach(this)
                .setContentView(R.layout.activity_settings)
                .setSwipeBackView(R.layout.swipeback_default);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getFragmentManager().beginTransaction()
                .replace(R.id.settings_fragment_container, new SettingsFragment())
                .commit();
    }
}
