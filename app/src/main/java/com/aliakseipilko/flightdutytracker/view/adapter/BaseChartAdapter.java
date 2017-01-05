package com.aliakseipilko.flightdutytracker.view.adapter;


import com.aliakseipilko.flightdutytracker.realm.model.Flight;

import io.realm.RealmResults;

public abstract class BaseChartAdapter {
    public abstract void process(RealmResults<Flight> flights);
}
