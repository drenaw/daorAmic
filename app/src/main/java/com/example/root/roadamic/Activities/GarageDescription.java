package com.example.root.roadamic.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.root.roadamic.R;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

public class GarageDescription extends AppCompatActivity {

    private BottomBar bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garage_description);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent i = getIntent();
        String title = i.getStringExtra("GarageName");
        setTitle(title);

        CoordinatorLayout coordinatorLayout = (CoordinatorLayout)findViewById(R.id.coordinator_garage_description);
        bottomBar = BottomBar.attachShy(coordinatorLayout,(NestedScrollView)findViewById(R.id.scrolling_garage_description),savedInstanceState);
        bottomBar.setItems(R.menu.menu_garage_description_bottom_bar);
        bottomBar.setOnMenuTabClickListener(new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                if(menuItemId == R.id.bottomBarItemOne){
                    TextView garage = (TextView)findViewById(R.id.garage_description);
                    garage.setText("Coming Soon!!\n\n"+getResources().getString(R.string.large_text));
                }else if(menuItemId == R.id.bottomBarItemTwo){
                    TextView garage = (TextView)findViewById(R.id.garage_description);
                    garage.setText("The garage isn't enlisted with us yet!");
                }else if(menuItemId == R.id.bottomBarItemThree){
                    TextView garage = (TextView)findViewById(R.id.garage_description);
                    garage.setText("Coming Soon!!\n" +
                            "\nEnter ratings for the Garage");
                }
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {

            }
        });
        bottomBar.mapColorForTab(0, ContextCompat.getColor(this,R.color.colorAccent));
        bottomBar.mapColorForTab(1, ContextCompat.getColor(this,R.color.colorAccent));
        bottomBar.mapColorForTab(2, ContextCompat.getColor(this,R.color.colorAccent));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Necessary to restore the BottomBar's state, otherwise we would
        // lose the current tab on orientation change.
        bottomBar.onSaveInstanceState(outState);
    }

}
