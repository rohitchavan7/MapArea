package com.ro.maparea.models;

import android.os.Parcel;
import android.os.Parcelable;

public class CustomLatLng implements Parcelable {

    private String latitude;
    private String Longitude;


    public CustomLatLng() {
    }

    public CustomLatLng(String latitude, String longitude) {
        this.latitude = latitude;
        Longitude = longitude;
    }

    protected CustomLatLng(Parcel in) {
        latitude = in.readString();
        Longitude = in.readString();
    }

    public static final Creator<CustomLatLng> CREATOR = new Creator<CustomLatLng>() {
        @Override
        public CustomLatLng createFromParcel(Parcel in) {
            return new CustomLatLng(in);
        }

        @Override
        public CustomLatLng[] newArray(int size) {
            return new CustomLatLng[size];
        }
    };

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    @Override
    public String toString() {
        return "CustomLatLng{" +
                "latitude='" + latitude + '\'' +
                ", Longitude='" + Longitude + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(latitude);
        dest.writeString(Longitude);
    }
}
