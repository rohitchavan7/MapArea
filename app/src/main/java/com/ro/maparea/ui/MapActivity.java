package com.ro.maparea.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.ro.maparea.R;
import com.ro.maparea.utils.GPSTracker;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;

    private FusedLocationProviderClient fusedLocationClient;


    private List<LatLng> mPolyLineList;
    private PolylineOptions mOptions;

    //vars
    private Boolean mLocationPermissionsGranted = false;
    private Boolean isDrawPolyline = false;
    private GoogleMap mMap;
    private FloatingActionButton mAddMarker;

    private int count = 4;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        getLocationPermission();

        mPolyLineList = new ArrayList<>();
        mOptions = new PolylineOptions().width(5).color(Color.CYAN).geodesic(true);


        mAddMarker = findViewById(R.id.add_marker);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {
        Toast.makeText(this, "Map is Ready", Toast.LENGTH_SHORT).show();
        mMap = googleMap;

        //add pin on map
        mAddMarker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LatLng currentLocation = new LatLng(googleMap.getMyLocation().getLatitude(), googleMap.getMyLocation().getLongitude());

                mMap.addMarker(new MarkerOptions()
                        .position(currentLocation)
                        .icon(BitmapDescriptorFactory
                                .defaultMarker(BitmapDescriptorFactory.HUE_RED)));

                mOptions.add(currentLocation);
                isDrawPolyline = true;
                mAddMarker.setVisibility(View.GONE);
            }
        });

        if (mLocationPermissionsGranted) {

            getCurrentLocation();

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);


            // drawing path as device move
            googleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                @Override
                public void onMyLocationChange(Location location) {
                    LatLng currentLocation = new LatLng(location.getLatitude(),location.getLongitude());

                    if (isDrawPolyline){
                        mPolyLineList.add(currentLocation);

                        mOptions.add(currentLocation);
                        mMap.addPolyline(mOptions);

                        listIntoJsonArray(mPolyLineList);

                    }


                }
            });

//            fusedLocationClient.getLastLocation()
//                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
//                        @Override
//                        public void onSuccess(Location location) {
//                            // Got last known location. In some rare situations this can be null.
//                            if (location != null) {
//                                LatLng currentLocation = new LatLng(location.getLatitude(),location.getLongitude());
//
//                                // Logic to handle location object
//                                if (isDrawPolyline){
//                                    //>>>--->>>> mPolyLineList.add(currentLocation);
//
//                                    mOptions.add(currentLocation);
//                                    mMap.addPolyline(mOptions);
//
//
//                                }
//                            }
//                        }
//                    });
        }
    }

    //LatLng ArrayList to jsonArray
    private void listIntoJsonArray(List<LatLng> latLngList){

        if (latLngList != null){

            // convert list to json
            Gson gson = new Gson();
            String jsonCartList = gson.toJson(latLngList);

            Toast.makeText(this, "json: " + jsonCartList, Toast.LENGTH_LONG).show();


        }




    }




    //current device location
    private void getCurrentLocation(){
        GPSTracker gps = new GPSTracker(this);
        if(gps.canGetLocation){
            final LatLng currentLocation = new LatLng(gps.getLatitude(),gps.getLongitude());
            moveCamera(currentLocation);

        }

    }

    private void moveCamera(LatLng latLng){
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM));

    }


    private void initMap(){
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(MapActivity.this);
    }

    private void getLocationPermission(){
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionsGranted = true;
                initMap();
            }else{
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionsGranted = false;

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        mLocationPermissionsGranted = false;
                        return;
                    }
                }
                mLocationPermissionsGranted = true;
                initMap();
            }
        }
    }



}
