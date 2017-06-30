package com.styleappteam.styleapp.connection_service;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by eduardo on 6/29/17.
 */

public class GetWorkers {
    @SerializedName("district_name")
    @Expose
    private String district_name;

    @SerializedName("longitude")
    @Expose
    private Double longitude;

    @SerializedName("latitude")
    @Expose
    private Double latitude;

    public String getDistrict_name() {
        return district_name;
    }

    public void setDistrict_name(String district_name) {
        this.district_name = district_name;
    }

    public Double getLongitude(Double longitude) {
        return this.longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
}
