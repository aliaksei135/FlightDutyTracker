package com.aliakseipilko.flightdutytracker.view.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aliakseipilko.flightdutytracker.R;
import com.aliakseipilko.flightdutytracker.view.adapter.HoursAdapter;
import com.aliakseipilko.flightdutytracker.view.fragment.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class HourFragment extends BaseFragment {

    @BindView(R.id.hours_tabs)
    TabLayout tabLayout;
    @BindView(R.id.hours_viewpager)
    ViewPager viewPager;

    private Unbinder unbinder;

    private HoursAdapter adapter;

    public HourFragment() {
        // Required empty public constructor
    }

    public static HourFragment newInstance() {
        return new HourFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hour, container, false);
        unbinder = ButterKnife.bind(this, view);

        adapter = new HoursAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager, true);

        return view;
    }

    @Override
    public void onFABClicked() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
