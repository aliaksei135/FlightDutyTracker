package com.aliakseipilko.flightdutytracker.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aliakseipilko.flightdutytracker.R;
import com.aliakseipilko.flightdutytracker.presenter.impl.FlightPresenter;
import com.aliakseipilko.flightdutytracker.view.adapter.FlightAdapter;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import xyz.danoz.recyclerviewfastscroller.vertical.VerticalRecyclerViewFastScroller;

public class FlightFragment extends Fragment {

    @BindView(R.id.flight_recylerview)
    SuperRecyclerView recyclerView;
    @BindView(R.id.flight_fast_scroller)
    VerticalRecyclerViewFastScroller scroller;
    private Unbinder unbinder;

    private FlightPresenter presenter;
    private FlightAdapter adapter;

    public FlightFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FlightFragment newInstance() {
        return new FlightFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_flight, container, false);
        unbinder = ButterKnife.bind(this, view);

        presenter = new FlightPresenter(view);
        presenter.subscribeAllCallbacks();

        adapter = new FlightAdapter();
        recyclerView.setAdapter(adapter);


        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
