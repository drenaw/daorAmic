package com.example.root.roadamic.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.root.roadamic.Models.GarageOnMap;
import com.example.root.roadamic.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Double latCurrentLocation = 28.7041;
    Double longCurrentLocation = 77.1025;
    private String TAG_INTENT_GARAGE_LIST_EXTRA = "GarageList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        String currentLocation;
        final String TAG_INTENT_SPLASHSCREEN_LOCATION = "LOCATION_EXTRA";
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                currentLocation= null;
            } else {
                currentLocation= extras.getString(TAG_INTENT_SPLASHSCREEN_LOCATION);
                String[] currentLocationArray = currentLocation.split(",");
                latCurrentLocation = Double.parseDouble(currentLocationArray[0]);
                longCurrentLocation = Double.parseDouble(currentLocationArray[1]);
            }
        } else {
            currentLocation= (String) savedInstanceState.getSerializable(TAG_INTENT_SPLASHSCREEN_LOCATION);
            String[] currentLocationArray = currentLocation.split(",");
            latCurrentLocation = Double.parseDouble(currentLocationArray[0]);
            longCurrentLocation = Double.parseDouble(currentLocationArray[1]);
        }


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng currentLatLong = new LatLng(latCurrentLocation,longCurrentLocation);
        Bitmap bitmap = BitmapFactory.decodeResource(getBaseContext().getResources(),R.drawable.ic_current_location);
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bitmap);
        mMap.addMarker(new MarkerOptions().position(currentLatLong).title("Current Location").icon(bitmapDescriptor));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(currentLatLong)      // Sets the center of the map to Mountain View
                .zoom(15)                   // Sets the zoom
                .bearing(10)                // Sets the orientation of the camera to south
                .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        final ArrayList<GarageOnMap> garageOnMapArrayList = getIntent().getParcelableArrayListExtra(TAG_INTENT_GARAGE_LIST_EXTRA);
        for(int i=0;i<garageOnMapArrayList.size();i++){
            LatLng garageLatLng = new LatLng(garageOnMapArrayList.get(i).getLocation().getLatitude(),garageOnMapArrayList.get(i).getLocation().getLongitude());
            Bitmap bit = BitmapFactory.decodeResource(getBaseContext().getResources(),R.drawable.ic_marker);
            BitmapDescriptor bitmapDesc = BitmapDescriptorFactory.fromBitmap(bit);
            mMap.addMarker(new MarkerOptions().position(garageLatLng).title(garageOnMapArrayList.get(i).getName()).icon(bitmapDesc));
        }

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Intent i = new Intent(getBaseContext(),GarageDescription.class);
                i.putExtra("GarageName",marker.getTitle());
                startActivity(i);
                return true;
            }
        });

    }
}
