package com.example.wannahouse.Activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.wannahouse.Class_Java.Data;
import com.example.wannahouse.Class_Java.House;
import com.example.wannahouse.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.GeoPoint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LocationActivity extends FragmentActivity implements OnMapReadyCallback {

    private FusedLocationProviderClient fusedLocationClient;

    private EditText et_find;
    private Button button_find;

    private GoogleMap mMap;
    ArrayList<LatLng> arrayListLatLng = new ArrayList<LatLng>();
    LatLng current;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        et_find = findViewById(R.id.et_find);
        button_find = findViewById(R.id.button_find);

        getLastLocation();

        arrayListLatLng.clear();

        button_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayListLatLng.clear();
                String target = et_find.getText().toString().trim();
                if (!target.isEmpty()) {
                    for (House house : Data.arrayListHouse) {
                        if (house.isVerify()) {
                            String address = house.getHouseNumber() + ", " + house.getStreet() + ", " + house.getWard() + ", " + house.getDistrict();
                            if (address.contains(target)) {
                                addMarker(address);
                            }
                        }
                    }
                    SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapLocation);
                    supportMapFragment.getMapAsync(LocationActivity.this);
                }
            }
        });

        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapLocation);
        supportMapFragment.getMapAsync(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng school = new LatLng(21.036779,105.782272);

        mMap.addMarker(new MarkerOptions().position(school)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
        .title("I'm here"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(school));
        Log.d("ZOOM", "ZOOm " + mMap.getMaxZoomLevel());
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));

        for (int i = 0; i < arrayListLatLng.size(); i++) {
//            if (i == 0) {
////                mMap.addMarker(new MarkerOptions().position(arrayListLatLng.get(i))
////                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))); }
            mMap.addMarker(new MarkerOptions().position(arrayListLatLng.get(i)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(arrayListLatLng.get(i)));
            Log.d("ZOOM", "ZOOm " + mMap.getMaxZoomLevel());
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public GeoPoint getLocationFromAddress(String strAddress) {
        Geocoder coder = new Geocoder(this);
        List<Address> address;
        GeoPoint p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new GeoPoint((double) (location.getLatitude()),
                    (double) (location.getLongitude()));
            Log.d("ZOOM", "z" + p1.getLatitude() + " " + p1.getLongitude());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return p1;
    }

    public void addMarker(String address) {
        GeoPoint xy = getLocationFromAddress(address);
        com.google.android.gms.maps.model.LatLng temp2 = new com.google.android.gms.maps.model.LatLng(xy.getLatitude(), xy.getLongitude());
        arrayListLatLng.add(temp2);
    }

    private void getLastLocation() {
        Log.d("LOCAAA", "L ");

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(LocationActivity.this);
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        Log.d("LOCAAA", "L 0");
                        if (location != null) {
                            current = new LatLng(location.getLatitude(), location.getLongitude());
                            Log.d("LOCAAA", "L 1" + current.latitude + " " + current.longitude);
                            Toast.makeText(LocationActivity.this, "L 1" + current.latitude + " " + current.longitude, Toast.LENGTH_SHORT).show();
                            SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapLocation);
                            supportMapFragment.getMapAsync(LocationActivity.this);
                        } else {
                            Toast.makeText(LocationActivity.this, "Not have location", Toast.LENGTH_SHORT).show();
                            Log.d("LOCAAA", "L 2");
                        }
                    }
                });
    }
}