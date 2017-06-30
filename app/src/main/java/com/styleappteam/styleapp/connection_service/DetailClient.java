package com.styleappteam.styleapp.connection_service;

import android.app.Service;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.styleappteam.styleapp.model.Client;
import com.styleappteam.styleapp.model.Services;
import com.styleappteam.styleapp.model.Worker;


/**
 * Created by eduardo on 6/30/17.
 */

public class DetailClient {
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("code")
    @Expose
    private String code;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("service")
    @Expose
    private Services service;

    @SerializedName("worker")
    @Expose
    private Worker worker;

    @SerializedName("client")
    @Expose
    private Client client;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Services getService() {
        return service;
    }

    public void setService(Services service) {
        this.service = service;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
