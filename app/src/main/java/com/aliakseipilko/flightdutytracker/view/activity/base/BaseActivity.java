package com.aliakseipilko.flightdutytracker.view.activity.base;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public abstract class BaseActivity extends AppCompatActivity {

    abstract protected void initComponents();

    public void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    public void showSnackbar(String message, boolean isSuccessful) {
        Snackbar sb = Snackbar.make(getWindow().getDecorView().getRootView(), message, Snackbar.LENGTH_LONG);

    }
}