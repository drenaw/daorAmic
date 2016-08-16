package com.example.root.roadamic.Activities;

import android.content.Intent;
import android.location.Location;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.root.roadamic.ApiImplementation.ApiClient;
import com.example.root.roadamic.ApiImplementation.ApiInterface;
import com.example.root.roadamic.Models.ApiResult;
import com.example.root.roadamic.Models.Garage;
import com.example.root.roadamic.Models.GarageOnMap;
import com.example.root.roadamic.R;
import com.google.android.gms.maps.model.LatLng;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import ernestoyaquello.com.verticalstepperform.VerticalStepperFormLayout;
import ernestoyaquello.com.verticalstepperform.interfaces.VerticalStepperForm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements VerticalStepperForm{

    double latCurrentLocation;
    double longCurrentLocation;
    private String currentLocation;
    private String TAG_INTENT_SPLASHSCREEN_LOCATION = "LOCATION_EXTRA";
    private String TAG_INTENT_GARAGE_LIST_EXTRA = "GarageList";
    private VerticalStepperFormLayout verticalStepperForm;
    private AVLoadingIndicatorView avi;
    NumberPicker Radius;
    EditText Type, Manufacturer, Problem;

    @Override
    public View createStepContentView(int stepNumber) {
        View view = null;
        switch (stepNumber){
            case 0: view = createRadiusStep();
                break;
            case 1: view = createVehicleType();
                break;
            case 2: view = createManufacturerStep();
                break;
            case 3: view = createProblemStep();
                break;
        }
        return view;
    }

    @Override
    public void onStepOpening(int stepNumber) {
        switch (stepNumber){
            case 0: verticalStepperForm.setStepAsCompleted(0);
                break;
            case 1: verticalStepperForm.setStepAsCompleted(1);
                break;
            case 2: verticalStepperForm.setStepAsCompleted(2);
                break;
            case 3: verticalStepperForm.setStepAsCompleted(3);
                break;
        }
    }

    @Override
    public void sendData() {
        verticalStepperForm.setVisibility(View.GONE);

        avi.smoothToShow();

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        int radius = Radius.getValue();
        String keyword = "Garage " + Type.getText().toString() + " " + Manufacturer.getText().toString();
        Call<ApiResult> call = apiInterface.getApiResults(currentLocation,radius,keyword,getResources().getString(R.string.google_maps_key));
        call.enqueue(new Callback<ApiResult>() {
            @Override
            public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
                List<Garage> garageList = response.body().getResults();
                ArrayList<GarageOnMap> garageOnMapList = new ArrayList<GarageOnMap>();

                for(int i=0;i<garageList.size();i++){
                    Location loc = new Location("");
                    loc.setLongitude(garageList.get(i).getGeometry().getLocation().getLng());
                    loc.setLatitude(garageList.get(i).getGeometry().getLocation().getLat());
                    GarageOnMap garageOnMap = new GarageOnMap(garageList.get(i).getName(),loc);
                    garageOnMapList.add(garageOnMap);
                }
                Intent i = new Intent(getBaseContext(),MapsActivity.class);
                i.putExtra(TAG_INTENT_SPLASHSCREEN_LOCATION,currentLocation);
                i.putParcelableArrayListExtra(TAG_INTENT_GARAGE_LIST_EXTRA,garageOnMapList);
                startActivity(i);
            }

            @Override
            public void onFailure(Call<ApiResult> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                currentLocation= null;
            } else {
                currentLocation= extras.getString(TAG_INTENT_SPLASHSCREEN_LOCATION);
            }
        } else {
            currentLocation= (String) savedInstanceState.getSerializable(TAG_INTENT_SPLASHSCREEN_LOCATION);
        }

        avi = (AVLoadingIndicatorView)findViewById(R.id.loader_splash_screen);
        avi.hide();
        String[] formSteps = {"Radius","Vehicle Type","Manufacturer Name","Problem"};
        int colorPrimary = ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary);
        int colorPrimaryDark = ContextCompat.getColor(getApplicationContext(),R.color.colorPrimaryDark);
        verticalStepperForm = (VerticalStepperFormLayout)findViewById(R.id.main_vertical_stepper_form);
        VerticalStepperFormLayout.Builder.newInstance(verticalStepperForm,formSteps,(VerticalStepperForm)this,this)
                .primaryColor(colorPrimary)
                .primaryDarkColor(colorPrimaryDark)
                .displayBottomNavigation(true)
                .init();

    }

    private View createRadiusStep(){
        Radius = new NumberPicker(this);
        Radius.setMinValue(1000);
        Radius.setMaxValue(10000);
        return Radius;
    }
    private View createVehicleType(){
        Type = new EditText(this);
        Type.setHint("Car / Bike / Cycle");
        Type.setSingleLine(true);
        return Type;
    }
    private View createManufacturerStep(){
        Manufacturer = new EditText(this);
        Manufacturer.setHint("Enter the name of the manufacturer");
        Manufacturer.setSingleLine(true);
        return  Manufacturer;
    }
    private View createProblemStep(){
        Problem = new EditText(this);
        Problem.setHint("Enter what problem you are having");
        Problem.setSingleLine(true);
        return Problem;
    }


}
