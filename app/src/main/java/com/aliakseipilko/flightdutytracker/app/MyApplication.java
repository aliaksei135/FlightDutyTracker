package com.aliakseipilko.flightdutytracker.app;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import butterknife.ButterKnife;
import io.realm.Realm;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

        Realm.init(getApplicationContext());
        ButterKnife.setDebug(true);
    }
}
