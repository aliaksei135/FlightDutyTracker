package com.aliakseipilko.flightdutytracker.view.activity.base;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.aliakseipilko.flightdutytracker.R;

import butterknife.BindColor;

public abstract class BaseActivity extends AppCompatActivity {

    @BindColor(R.color.successColor)
    int successColor;
    @BindColor(R.color.warningColor)
    int warningColor;
    @BindColor(R.color.failureColor)
    int failureColor;

    abstract protected void initComponents();

    public void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    public void showSnackbar(String message, boolean isSuccessful) {
        Snackbar sb = Snackbar.make(findViewById(R.id.root_coord_lo), message, Snackbar.LENGTH_LONG);

        if (isSuccessful) {
            sb.getView().setBackgroundColor(successColor);
        } else {
            sb.getView().setBackgroundColor(failureColor);
        }

        sb.show();
    }
}