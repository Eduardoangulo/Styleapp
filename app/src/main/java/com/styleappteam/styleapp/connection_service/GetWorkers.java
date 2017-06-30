package com.styleappteam.styleapp.connection_service;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.styleappteam.styleapp.model.Address;
import com.styleappteam.styleapp.model.Worker;

import java.util.List;

/**
 * Created by eduardo on 6/29/17.
 */

public class GetWorkers {

    @SerializedName("success")
    @Expose
    private Boolean success;

    @SerializedName("workers")
    @Expose
    private List<Worker> workers=null;

    public List<Worker> getWorkers() {
        return workers;
    }

    public void setWorkers(List<Worker> workers) {
        this.workers = workers;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
