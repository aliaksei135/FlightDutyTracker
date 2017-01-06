package com.aliakseipilko.flightdutytracker.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aliakseipilko.flightdutytracker.R;
import com.aliakseipilko.flightdutytracker.presenter.impl.FlightPresenter;
import com.aliakseipilko.flightdutytracker.view.activity.CreateFlightActivity;
import com.aliakseipilko.flightdutytracker.view.activity.FlightDetailsActivity;
import com.aliakseipilko.flightdutytracker.view.adapter.FlightAdapter;
import com.aliakseipilko.flightdutytracker.view.fragment.base.BaseFragment;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.malinskiy.superrecyclerview.swipe.SwipeDismissRecyclerViewTouchListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FlightFragment extends BaseFragment implements FlightAdapter.onRecyclerItemClickListener {

    @BindView(R.id.flight_recylerview)
    SuperRecyclerView recyclerView;
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
        recyclerView.setupMoreListener(new OnMoreListener() {
            @Override
            public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
                if (adapter.getItemCount() <= maxLastVisiblePosition) {
                    recyclerView.setLoadingMore(false);
                    recyclerView.hideMoreProgress();
                    return;
                }
                recyclerView.showMoreProgress();
                presenter.getMultipleFlightsByIdCount(overallItemsCount, 10);
            }
        }, 7);
        recyclerView.hideMoreProgress();
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

    }

    @Override
    public void onFABClicked() {
        getActivity().startActivity(new Intent(getContext(), CreateFlightActivity.class));
    }

    public void setAdapter(FlightAdapter adapter) {
        this.adapter = adapter;
        adapter.setItemClickCallback(this);
    }

    public void setRefreshing(boolean refreshing) {
        if (recyclerView != null) {
            recyclerView.setRefreshing(refreshing);
        }
    }

    public void setLoadingMore(boolean loadingMore) {
        if (recyclerView != null) {
            recyclerView.setLoadingMore(loadingMore);
            if (loadingMore) {
                recyclerView.showMoreProgress();
            } else {
                recyclerView.hideMoreProgress();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onItemClicked(long flightId) {
        Intent intent = new Intent(getContext(), FlightDetailsActivity.class);
        intent.putExtra("flightId", flightId);
        getActivity().startActivity(intent);
    }
}
