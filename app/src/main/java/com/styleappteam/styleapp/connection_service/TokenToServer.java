package com.styleappteam.styleapp.connection_service;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by eduardo on 6/30/17.
 */

public class TokenToServer {

    @SerializedName("client_id")
    @Expose
    private int client_id;
    @SerializedName("worker_id")
    @Expose
    private int worker_id;
    @SerializedName("server_id")
    @Expose
    private int server_id;
    @SerializedName("token")
    @Expose
    private String token;

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public int getWorker_id() {
        return worker_id;
    }

    public void setWorker_id(int worker_id) {
        this.worker_id = worker_id;
    }

    public int getServer_id() {
        return server_id;
    }

    public void setServer_id(int server_id) {
        this.server_id = server_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
