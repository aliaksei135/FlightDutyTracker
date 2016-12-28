package com.aliakseipilko.flightdutytracker.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aliakseipilko.flightdutytracker.R;
import com.aliakseipilko.flightdutytracker.presenter.impl.FlightPresenter;
import com.aliakseipilko.flightdutytracker.view.adapter.FlightAdapter;
import com.aliakseipilko.flightdutytracker.view.fragment.base.BaseFragment;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.malinskiy.superrecyclerview.swipe.SwipeDismissRecyclerViewTouchListener;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import xyz.danoz.recyclerviewfastscroller.vertical.VerticalRecyclerViewFastScroller;

public class FlightFragment extends BaseFragment {

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

        adapter = new FlightAdapter(getContext(), null);

        presenter = new FlightPresenter(this, adapter);
        presenter.subscribeAllCallbacks();

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -7);
        //Get all flights between today and 7 days ago to start with
        presenter.getMultipleFlightsByDateRange(new Date(), cal.getTime());

        adapter = new FlightAdapter(getContext(), null);
        setupRecyclerView();

        return view;
    }

    private void setupRecyclerView() {

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setAdapter(adapter);
        recyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showError("Not implemented yet!");
                recyclerView.setRefreshing(false);
            }
        });
        recyclerView.setupMoreListener(new OnMoreListener() {
            @Override
            public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {

                presenter.getMultipleFlightsByIdCount(overallItemsCount + 1, 15);
            }
        }, 10);
        recyclerView.setupSwipeToDismiss(new SwipeDismissRecyclerViewTouchListener.DismissCallbacks() {
            @Override
            public boolean canDismiss(int position) {
                //No Cards are dismissable
                return false;
            }

            @Override
            public void onDismiss(RecyclerView recyclerView, int[] reverseSortedPositions) {

            }

        });

        scroller.setRecyclerView(recyclerView.getRecyclerView());
        recyclerView.setOnScrollListener(scroller.getOnScrollListener());
    }

    public void onFABClicked() {

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
