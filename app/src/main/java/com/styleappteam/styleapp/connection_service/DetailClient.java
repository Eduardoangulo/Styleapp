package com.styleappteam.styleapp.connection_service;

import android.app.Service;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.styleappteam.styleapp.model.Client;
import com.styleappteam.styleapp.model.Services;
import com.styleappteam.styleapp.model.Worker;

import java.util.List;


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
    private Integer status;

    @SerializedName("value")
    @Expose
    private Integer value;

    @SerializedName("service")
    @Expose
    private Services service;

    @SerializedName("worker")
    @Expose
    private List<Worker> worker;

    @SerializedName("client")
    @Expose
    private List<Client> client;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Services getService() {
        return service;
    }

    public void setService(Services service) {
        this.service = service;
    }

    public List<Worker> getWorker() {
        return worker;
    }

    public void setWorker(List<Worker> worker) {
        this.worker = worker;
    }

    public List<Client> getClient() {
        return client;
    }

    public void setClient(List<Client> client) {
        this.client = client;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
