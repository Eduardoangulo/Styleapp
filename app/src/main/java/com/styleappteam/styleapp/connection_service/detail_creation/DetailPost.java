package com.styleappteam.styleapp.connection_service.detail_creation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Luis on 04/07/2017.
 */

public class DetailPost {
    @SerializedName("worker_id")
    @Expose
    private Integer workerId;
    @SerializedName("client_id")
    @Expose
    private Integer clientId;
    @SerializedName("service_id")
    @Expose
    private Integer serviceId;

    public DetailPost(Integer workerId, Integer clientId, Integer serviceId) {
        this.workerId = workerId;
        this.clientId = clientId;
        this.serviceId = serviceId;
    }

    public Integer getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Integer workerId) {
        this.workerId = workerId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }
}
