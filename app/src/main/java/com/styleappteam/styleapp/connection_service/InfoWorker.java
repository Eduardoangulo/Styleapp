package com.styleappteam.styleapp.connection_service;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by eduardo on 6/30/17.
 */

public class InfoWorker {
    @SerializedName("district_name")
    @Expose
    private String district_name;

    @SerializedName("longitude")
    @Expose
    private Double longitude;

    @SerializedName("latitude")
    @Expose
    private Double latitude;

    @SerializedName("service_id")
    @Expose
    private Integer service_id;

    public String getDistrict_name() {
        return district_name;
    }

    public void setDistrict_name(String district_name) {
        this.district_name = district_name;
    }

    public Double getLongitude() {
        return longitude;
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

    public Integer getService_id() {
        return service_id;
    }

    public void setService_id(Integer service_id) {
        this.service_id = service_id;
    }
}
