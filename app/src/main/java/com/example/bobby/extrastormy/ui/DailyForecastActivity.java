package com.example.bobby.extrastormy.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;


import android.support.v4.view.GravityCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.bobby.extrastormy.R;
import com.example.bobby.extrastormy.adapters.DayAdapter;
import com.example.bobby.extrastormy.weather.Day;

import java.lang.reflect.Array;
import java.util.Arrays;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class DailyForecastActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    String parseLocation;
    String latitude;
    String longitude;
    private Day[] mDays;
    Parcelable[] hourParcelables;
    Parcelable[] dayParcelables;

    @InjectView(R.id.dailyRecycler)
    RecyclerView mRecyclerView;

    @InjectView(R.id.locationLabel)
    TextView mLocationLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.inject(this);

        Intent intent = getIntent();
        latitude = intent.getStringExtra((getString(R.string.latKey)));
        longitude = intent.getStringExtra((getString(R.string.longKey)));
        parseLocation = intent.getStringExtra((getString(R.string.locKey)));
        dayParcelables = intent.getParcelableArrayExtra(getString(R.string.daysKey));
        hourParcelables = intent.getParcelableArrayExtra(getString(R.string.hoursKey));

        mDays = Arrays.copyOf(dayParcelables, dayParcelables.length-1, Day[].class);

        DayAdapter adapter = new DayAdapter(mDays);
        mRecyclerView.setAdapter(adapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);

        mLocationLabel.setText(parseLocation);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();

        if (id == R.id.nav_current) {
            startCurrentActivity();

        } else if (id == R.id.nav_hourly) {

            startHourlyActivity();

        } else if (id == R.id.nav_daily) {
            startDailyActivity();

        } else if (id == R.id.nav_maps) {
            startMapsActivity();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void startMapsActivity() {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    public void startCurrentActivity() {
        Intent intent = new Intent(this, CurrentActivity.class);
        intent.putExtra((getString(R.string.latKey)), latitude);
        intent.putExtra((getString(R.string.longKey)), longitude);
        intent.putExtra((getString(R.string.locKey)), parseLocation);
        startActivity(intent);
    }

    public void startHourlyActivity() {
        Intent intent = new Intent(this, HourlyForecastActivity.class);
        intent.putExtra(getString(R.string.hoursKey), hourParcelables);
        intent.putExtra(getString(R.string.daysKey), dayParcelables);
        intent.putExtra((getString(R.string.locKey)), parseLocation);
        intent.putExtra((getString(R.string.latKey)), latitude);
        intent.putExtra((getString(R.string.longKey)), longitude);
        startActivity(intent);
    }

    public void startDailyActivity() {
        // Already in DailyForecastActivity
    }

}
