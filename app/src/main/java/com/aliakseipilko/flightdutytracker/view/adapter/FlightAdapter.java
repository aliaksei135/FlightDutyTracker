package com.aliakseipilko.flightdutytracker.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aliakseipilko.flightdutytracker.realm.model.Flight;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;

public class FlightAdapter extends RealmRecyclerViewAdapter<Flight, FlightAdapter.FlightViewHolder> {

    private RealmResults<Flight> flights;
    private OnItemClickListener onItemClickListener;

    public FlightAdapter(@NonNull Context context, @Nullable OrderedRealmCollection<Flight> data, boolean autoUpdate) {
        super(context, data, autoUpdate);
    }

    @Override
    public FlightViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FlightViewHolder(null);//TODO Get a proper view
    }

    @Override
    public void onBindViewHolder(FlightViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return flights.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(long id);
    }

    public class FlightViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mTextView;

        public FlightViewHolder(TextView v) {
            super(v);
            mTextView = v;
        }

        @Override
        public void onClick(View view) {
            Flight selectedFlight = flights.get(getAdapterPosition());

        }
    }
}
