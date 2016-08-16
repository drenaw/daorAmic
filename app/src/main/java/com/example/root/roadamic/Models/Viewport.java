package com.example.root.roadamic.Models;

import android.location.Location;

import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 15/8/16.
 */
public class Viewport {

    @SerializedName("northeast")
    private location northeast;

    @SerializedName("southwest")
    private location southwest;

    public Viewport(location northeast, location southwest) {
        this.northeast = northeast;
        this.southwest = southwest;
    }

    public location getNortheast() {
        return northeast;
    }

    public void setNortheast(location northeast) {
        this.northeast = northeast;
    }

    public location getSouthwest() {
        return southwest;
    }

    public void setSouthwest(location southwest) {
        this.southwest = southwest;
    }

}
