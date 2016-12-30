package com.aliakseipilko.flightdutytracker.view.activity.base;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public abstract class BaseActivity extends AppCompatActivity {

    abstract protected void initComponents();

    abstract public void showSnackbar(String message, boolean isSuccess);

    public void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}