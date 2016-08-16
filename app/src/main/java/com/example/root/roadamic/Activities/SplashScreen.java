package com.example.root.roadamic.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.root.roadamic.Utils.PermissionUtils;
import com.example.root.roadamic.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

public class SplashScreen extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    public GoogleApiClient googleApiClient;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static int LOCATION_PERMISSION_REPLIED = 0;
    private Location myCurrentLocation = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    android.Manifest.permission.ACCESS_FINE_LOCATION, true);
            LOCATION_PERMISSION_REPLIED=1;
        }else{

            LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
            if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && !locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
                Toast toast = Toast.makeText(getApplicationContext(), "TURN LOCATIONS ON", Toast.LENGTH_SHORT);
                toast.show();
            }
            if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                myCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            }
            if(myCurrentLocation==null){
                myCurrentLocation = new Location("");
                myCurrentLocation.setLatitude(28.7041);
                myCurrentLocation.setLongitude(77.1025);
            }
            double latitudeCurrentLocation = myCurrentLocation.getLatitude();
            double longitudeCurrentLocation = myCurrentLocation.getLongitude();
            final String currentLocation = "" + latitudeCurrentLocation + "," + longitudeCurrentLocation;
            final String TAG_INTENT_SPLASHSCREEN_LOCATION = "LOCATION_EXTRA";
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    intent.putExtra(TAG_INTENT_SPLASHSCREEN_LOCATION,currentLocation);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                    finish();
                }
            }, 2000);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Snackbar snackbar = Snackbar.make(getCurrentFocus(),"Turn Loc On",Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Snackbar snackbar = Snackbar.make(getCurrentFocus(),"Unable to access Loc settings",Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    protected void onStart() {
        googleApiClient.connect();
        super.onStart();
    }
    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            Snackbar snackbar = Snackbar.make(getCurrentFocus(),"Please allow Location Permissions to continue",Snackbar.LENGTH_LONG);
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
            if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && !locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
                Toast toast = Toast.makeText(getApplicationContext(), "TURN LOCATIONS ON", Toast.LENGTH_SHORT);
                toast.show();
            }
            if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                try{
                    myCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                }catch (SecurityException se){
                    se.printStackTrace();
                }
            }
            if(myCurrentLocation==null){
                myCurrentLocation = new Location("");
                myCurrentLocation.setLatitude(28.7041);
                myCurrentLocation.setLongitude(77.1025);
            }
            double latitudeCurrentLocation = myCurrentLocation.getLatitude();
            double longitudeCurrentLocation = myCurrentLocation.getLongitude();
            final String currentLocation = "" + latitudeCurrentLocation + "," + longitudeCurrentLocation;
            final String TAG_INTENT_SPLASHSCREEN_LOCATION = "LOCATION_EXTRA";
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    intent.putExtra(TAG_INTENT_SPLASHSCREEN_LOCATION,currentLocation);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                    finish();
                }
            }, 2000);

        } else {
            Snackbar snackbar = Snackbar.make(getCurrentFocus(),"Please allow location access from Settings",Snackbar.LENGTH_LONG);
            snackbar.show();
            // Display the missing permission error dialog when the fragments resume.
        }
    }
}
