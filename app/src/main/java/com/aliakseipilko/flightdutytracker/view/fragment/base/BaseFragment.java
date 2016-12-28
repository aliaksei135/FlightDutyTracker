package com.aliakseipilko.flightdutytracker.view.fragment.base;


import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;

import com.aliakseipilko.flightdutytracker.R;

public abstract class BaseFragment extends Fragment {

    public abstract void onFABClicked();

    public void showError(String message) {
        Snackbar sb = Snackbar.make(getView(), message, Snackbar.LENGTH_LONG);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            sb.getView().setBackgroundColor(getResources().getColor(R.color.failureColor, null));
        } else {
            sb.getView().setBackgroundColor(getResources().getColor(R.color.failureColor));
        }
        sb.show();
    }

    public void showSuccess(String message) {
        Snackbar sb = Snackbar.make(getView(), message, Snackbar.LENGTH_LONG);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            sb.getView().setBackgroundColor(getResources().getColor(R.color.successColor, null));
        } else {
            sb.getView().setBackgroundColor(getResources().getColor(R.color.successColor));
        }
        sb.show();
    }
}
