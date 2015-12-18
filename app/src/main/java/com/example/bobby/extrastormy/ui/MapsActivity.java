package com.example.bobby.extrastormy.ui;

import com.example.bobby.extrastormy.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.DialogFragment;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * This shows how to listen to some {@link GoogleMap} events.
 */
public class MapsActivity extends AppCompatActivity
        implements OnMapClickListener,
        OnMapReadyCallback {

    public static final String TAG = MapsActivity.class.getSimpleName();
    private TextView mTapTextView;
    String tapLat;
    String tapLong;
    String parseLocation;
    LatLng tap;
    ViewGroup container;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mTapTextView = (TextView) findViewById(R.id.tap_text);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



    }

    @Override
    public void onMapReady(final GoogleMap gMap) {
        gMap.setOnMapClickListener(this);
        gMap.setMyLocationEnabled(true);
        //map.addMarker(new MarkerOptions().position(new LatLng(10, 10)).title(""));



    }

    @Override
    public void onMapClick(LatLng point) {


        Log.i(TAG, point + "");

        String tapped = point + "";
        String tapLatLong = tapped.substring(tapped.indexOf("(") + 1, tapped.indexOf(")"));
        tapLat = tapped.substring(tapped.indexOf("(") + 1, tapped.indexOf(","));
        tapLong = tapped.substring(tapped.indexOf(",") + 1, tapped.indexOf(")"));
        Double tapLatDouble = Double.parseDouble(tapLat);
        Double tapLongDouble = Double.parseDouble(tapLong);

        Log.i(TAG, ":" + tapLatLong);

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(tapLatDouble, tapLongDouble, 1);


            if (!addresses.isEmpty()) {
                Address address = addresses.get(0);
                String location = String.valueOf(addresses.get(0));

                Log.i(TAG, ":" + location + "");

                String locality = address.getLocality();
                String adminArea = address.getAdminArea();
                String country = address.getCountryCode();

                if (locality != null && adminArea != null) {
                    parseLocation = locality + ", "
                                    + adminArea + ", "
                                    + country;
                } else if (locality == null && adminArea != null) {
                    parseLocation = adminArea + ", " + country;
                } else if ((locality != null) && (adminArea == null)) {
                    parseLocation = locality + ", " + country;
                } else if (country != null) {
                    parseLocation = country;

                } else {
                    parseLocation = "Unknown Location";
                }

                mTapTextView.setText(parseLocation);

                showDialog();

            } else {
                mTapTextView.setText("Try another location");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void showDialog() {
        DialogFragment newFragment = ThisLocationDialogFragment.newInstance(R.string.alert_dialog_two_buttons_title);
        newFragment.show(getFragmentManager(), "dialog");
    }

    public void doPositiveClick() {
        Intent intent = new Intent(this, CurrentActivity.class);
        Log.i("FragmentAlertDialog", "Positive click!");

        intent.putExtra((getString(R.string.latKey)), tapLat);
        intent.putExtra((getString(R.string.longKey)), tapLong);
        intent.putExtra((getString(R.string.locKey)), parseLocation);

        Log.i(TAG, "LAT FROM MAP INTENT:" + tapLat);
        Log.i(TAG, "LONG FROM MAP INTENT:" + tapLong);
        Log.i(TAG, "LOC FROM MAP INTENT:" + parseLocation);

        mTapTextView.setText(R.string.tap_a_location);

        startActivity(intent);
    }

    public void doNegativeClick() {
        // Do stuff here.
        Log.i("FragmentAlertDialog", "Negative click!");
    }

}