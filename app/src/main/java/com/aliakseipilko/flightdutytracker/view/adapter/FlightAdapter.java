package com.aliakseipilko.flightdutytracker.view.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aliakseipilko.flightdutytracker.R;
import com.aliakseipilko.flightdutytracker.realm.model.Flight;
import com.aliakseipilko.flightdutytracker.view.fragment.FlightFragment;

import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.OrderedRealmCollection;
import io.realm.RealmList;
import io.realm.RealmRecyclerViewAdapter;

public class FlightAdapter extends RealmRecyclerViewAdapter<Flight, FlightAdapter.FlightViewHolder> {

    private OrderedRealmCollection<Flight> flights;

    private onRecyclerItemClickListener itemClickCallback;

    private FlightFragment fragment;

    public FlightAdapter(@NonNull FlightFragment fragment, @Nullable OrderedRealmCollection<Flight> data) {
        super(fragment.getContext(), data, true);
        flights = data;
        this.fragment = fragment;
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
        //TODO Allow user to switch between IATA and ICAO
        //Hardcode IATA for now
        if (TextUtils.isEmpty(flight.getDepartureIATACode())) {
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
