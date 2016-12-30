package com.aliakseipilko.flightdutytracker.app;

import android.app.Application;

import butterknife.ButterKnife;
import io.realm.Realm;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(getApplicationContext());
        ButterKnife.setDebug(true);
    }
}
