package com.aliakseipilko.flightdutytracker.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aliakseipilko.flightdutytracker.R;
import com.aliakseipilko.flightdutytracker.realm.model.Flight;

import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

public class FlightAdapter extends RealmRecyclerViewAdapter<Flight, FlightAdapter.FlightViewHolder> {

    private OrderedRealmCollection<Flight> flights;

    public FlightAdapter(@NonNull Context context, @Nullable OrderedRealmCollection<Flight> data) {
        super(context, data, true);
        flights = data;
    }

    @Override
    public FlightViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_item_flight, parent);
        return new FlightViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FlightViewHolder holder, int position) {

        Flight flight = flights.get(position);

        holder.fltNumTv.setText(flight.getFlightNumber());
        //TODO Allow user to switch between IATA and ICAO
        //Hardcode IATA for now
        holder.depCodeTv.setText(flight.getDepartureIATACode());
        holder.arrCodeTv.setText(flight.getArrivalIATACode());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
        holder.dateTv.setText(dateFormat.format(flight.getStartDutyTime()));
    }

    @Override
    public int getItemCount() {

        return flights == null ? 0 : flights.size();
    }

    public void addMoreData(OrderedRealmCollection<Flight> moreFlights) {
        for (Flight f : moreFlights) {
            flights.add(f);
        }
    }


    public class FlightViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.flight_number_vh_tv)
        TextView fltNumTv;
        @BindView(R.id.departure_code_vh_tv)
        TextView depCodeTv;
        @BindView(R.id.arrival_code_vh_tv)
        TextView arrCodeTv;
        @BindView(R.id.date_vh_tv)
        TextView dateTv;

        public FlightViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //TODO Switch to flight details fragment
        }
    }
}
