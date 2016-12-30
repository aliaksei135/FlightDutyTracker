package com.aliakseipilko.flightdutytracker.view.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.aliakseipilko.flightdutytracker.R;
import com.aliakseipilko.flightdutytracker.view.activity.base.BaseActivity;
import com.aliakseipilko.flightdutytracker.view.fragment.FlightFragment;
import com.aliakseipilko.flightdutytracker.view.fragment.base.BaseFragment;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //Fragment constants
    static final String FRAGMENT_FLIGHT = "FRAGMENT_FLIGHT";
    static final String FRAGMENT_DUTIES = "FRAGMENT_DUTIES";
    static final String FRAGMENT_HOURS = "FRAGMENT_HOURS";
    static String CURRENT_FRAGMENT_TAG;
    @BindColor(R.color.successColor)
    int successColor;
    @BindColor(R.color.warningColor)
    int warningColor;
    @BindColor(R.color.failureColor)
    int failureColor;
    @BindView(R.id.main_toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.main_fragment_container)
    FrameLayout fragmentContainer;

    @Override
    protected void initComponents() {

    }

    @Override
    public void showSnackbar(String message, boolean isSuccess) {
        Snackbar sb = Snackbar.make(findViewById(R.id.root_coord_lo), message, Snackbar.LENGTH_LONG);

        if (isSuccess) {
            sb.getView().setBackgroundColor(successColor);
        } else {
            sb.getView().setBackgroundColor(failureColor);
        }

        sb.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.main_fragment_container);
                if (currentFragment instanceof BaseFragment) {
                    ((BaseFragment) currentFragment).onFABClicked();
                } else {
                    showSnackbar("Not implemented yet!", false);
                }
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (CURRENT_FRAGMENT_TAG != null) {
            showCurrentFragment();
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("CURRENT_FRAGMENT", CURRENT_FRAGMENT_TAG);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        CURRENT_FRAGMENT_TAG = savedInstanceState.getString("CURRENT_FRAGMENT");
        showCurrentFragment();
    }

    void showCurrentFragment() {

        Fragment newFragment = null;

        switch (CURRENT_FRAGMENT_TAG) {
            case FRAGMENT_FLIGHT:
                //Set Fragment to switch
                newFragment = FlightFragment.newInstance();

                //Change FAB to 'add' icon
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    fab.setImageDrawable(getDrawable(R.drawable.ic_add_white));
                } else {
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_white));
                }
                break;
            case FRAGMENT_DUTIES:

                break;
            case FRAGMENT_HOURS:

                break;
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_fragment_container, newFragment, CURRENT_FRAGMENT_TAG);
        transaction.commitNow();
        drawer.closeDrawer(GravityCompat.START);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment newFragment = null;

        switch (id) {
            case R.id.nav_flights:

                //Set Fragment to switch
                newFragment = FlightFragment.newInstance();
                CURRENT_FRAGMENT_TAG = FRAGMENT_FLIGHT;

                //Change FAB to 'add' icon
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    fab.setImageDrawable(getDrawable(R.drawable.ic_add_white));
                } else {
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_white));
                }
                break;
            case R.id.nav_gallery:

                break;
            case R.id.nav_slideshow:

                break;
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_fragment_container, newFragment, CURRENT_FRAGMENT_TAG);
        transaction.commitNow();
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
