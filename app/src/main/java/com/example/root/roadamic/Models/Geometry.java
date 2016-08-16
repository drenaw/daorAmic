package com.example.root.roadamic.Models;

import android.location.Location;

import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 15/8/16.
 */
public class Geometry {

    @SerializedName("location")
    private location location;

    @SerializedName("viewport")
    private Viewport viewport;

    public Geometry(location location, Viewport viewport) {
        this.location = location;
        this.viewport = viewport;
    }

    public location getLocation() {
        return location;
    }

    public void setLocation(location location) {
        this.location = location;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }
}
