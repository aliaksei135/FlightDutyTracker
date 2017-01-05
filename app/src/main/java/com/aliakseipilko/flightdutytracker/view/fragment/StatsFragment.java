package com.aliakseipilko.flightdutytracker.view.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aliakseipilko.flightdutytracker.R;
import com.aliakseipilko.flightdutytracker.view.adapter.StatsAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class StatsFragment extends Fragment {

    @BindView(R.id.stats_tabs)
    TabLayout tabLayout;
    @BindView(R.id.stats_viewpager)
    ViewPager viewPager;

    FragmentPagerAdapter adapter;

    private Unbinder unbinder;

    public StatsFragment() {
        // Required empty public constructor
    }

    public static StatsFragment newInstance() {

        return new StatsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stats, container, false);
        unbinder = ButterKnife.bind(this, view);

        adapter = new StatsAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager, true);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
