package com.example.root.roadamic.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 16/8/16.
 */
public class location {

    @SerializedName("lat")
    private Double lat;

    @SerializedName("lng")
    private Double lng;

    public location(Double lat, Double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
}
