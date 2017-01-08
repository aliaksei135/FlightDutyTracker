/*
 * Copyright (C) 2017 Aliaksei Pilko
 *
 * Licensed under the Apache License, Version 2.0 (the “License”); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an “AS IS” BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package com.aliakseipilko.flightdutytracker.view.adapter;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aliakseipilko.flightdutytracker.R;
import com.aliakseipilko.flightdutytracker.dagger.components.DaggerStorageComponent;
import com.aliakseipilko.flightdutytracker.dagger.modules.PrefsModule;
import com.aliakseipilko.flightdutytracker.realm.model.Flight;
import com.aliakseipilko.flightdutytracker.view.fragment.FlightFragment;

import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.OrderedRealmCollection;
import io.realm.RealmList;
import io.realm.RealmRecyclerViewAdapter;

public class FlightAdapter extends RealmRecyclerViewAdapter<Flight, FlightAdapter.FlightViewHolder> {

    @Inject
    SharedPreferences prefs;
    private OrderedRealmCollection<Flight> flights;
    private onRecyclerItemClickListener itemClickCallback;
    private FlightFragment fragment;

    public FlightAdapter(@NonNull FlightFragment fragment, @Nullable OrderedRealmCollection<Flight> data) {
        super(fragment.getContext(), data, true);
        flights = data;
        this.fragment = fragment;
        DaggerStorageComponent.builder().prefsModule(new PrefsModule(fragment.getContext().getApplicationContext())).build().inject(this);
    }

    @Override
    public FlightViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_item_flight, parent, false);

        return new FlightViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final FlightViewHolder holder, int position) {

        Flight flight = flights.get(position);

        holder.fltNumTv.setText(flight.getFlightNumber());

        if (prefs.getString("airportCodeType", "IATA").equals("ICAO")) {
            holder.depCodeTv.setText(flight.getDepartureICAOCode());
            holder.arrCodeTv.setText(flight.getArrivalICAOCode());
        } else {
            holder.depCodeTv.setText(flight.getDepartureIATACode());
            holder.arrCodeTv.setText(flight.getArrivalIATACode());
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
        holder.dateTv.setText(dateFormat.format(flight.getStartDutyTime()));
        holder.flightId = flight.getId();
        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickCallback.onItemClicked(holder.getFlightId());
            }
        });
    }

    @Override
    public int getItemCount() {

        return flights == null ? 0 : flights.size();
    }

    public void addMoreData(OrderedRealmCollection<Flight> moreFlights) {
        if (flights == null) {
            flights = new RealmList<>();
        }
        for (Flight f : moreFlights) {
            flights.add(f);
        }
//        notifyDataSetChanged();
        fragment.setLoadingMore(false);
    }

    public void setItemClickCallback(onRecyclerItemClickListener itemClickCallback) {
        this.itemClickCallback = itemClickCallback;
    }

    public interface onRecyclerItemClickListener {
        void onItemClicked(long flightId);
    }

    public class FlightViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.flight_number_vh_tv)
        TextView fltNumTv;
        @BindView(R.id.departure_code_vh_tv)
        TextView depCodeTv;
        @BindView(R.id.arrival_code_vh_tv)
        TextView arrCodeTv;
        @BindView(R.id.date_vh_tv)
        TextView dateTv;

        long flightId;

        View.OnClickListener onClickListener;
        View view;

        public FlightViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            view = v;
        }

        public void setOnClickListener(View.OnClickListener listener) {
            onClickListener = listener;
            view.setOnClickListener(listener);
        }

        public long getFlightId() {
            return flightId;
        }
    }
}
