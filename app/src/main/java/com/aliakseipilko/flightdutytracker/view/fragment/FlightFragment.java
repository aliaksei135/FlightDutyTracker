package com.aliakseipilko.flightdutytracker.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aliakseipilko.flightdutytracker.R;
import com.aliakseipilko.flightdutytracker.presenter.impl.FlightPresenter;
import com.aliakseipilko.flightdutytracker.view.activity.CreateFlightActivity;
import com.aliakseipilko.flightdutytracker.view.adapter.FlightAdapter;
import com.aliakseipilko.flightdutytracker.view.fragment.base.BaseFragment;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.malinskiy.superrecyclerview.swipe.SwipeDismissRecyclerViewTouchListener;

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

        presenter = new FlightPresenter(this);
        presenter.subscribeAllCallbacks();
        presenter.initAdapaterData();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_flight, container, false);
        unbinder = ButterKnife.bind(this, view);

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

        if (adapter.getItemCount() == 0) {
            hideScroller();
        }
    }

    @Override
    public void onFABClicked() {
        getActivity().startActivity(new Intent(getContext(), CreateFlightActivity.class));
    }

    public FlightAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(FlightAdapter adapter) {
        this.adapter = adapter;
    }

    public void showScroller() {
        scroller.setVisibility(View.VISIBLE);
    }

    public void hideScroller() {
        scroller.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
