package com.example.root.roadamic.Models;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by root on 15/8/16.
 */
public class GarageOnMap implements Parcelable {

    private Location location;
    private String name;

    public GarageOnMap(String name, Location location) {
        this.name = name;
        this.location = location;
    }
    public GarageOnMap(Parcel parcel){
        this.name = parcel.readString();
        this.location = Location.CREATOR.createFromParcel(parcel);
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        location.writeToParcel(parcel,i);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){
        @Override
        public Object createFromParcel(Parcel parcel) {
            return new GarageOnMap(parcel);
        }

        @Override
        public Object[] newArray(int i) {
            return new Object[i];
        }
    };



}
