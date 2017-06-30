package com.styleappteam.styleapp.connection_service;

/**
 * Created by Luis on 30/06/2017.
 */

public class clientDetailPost {
    private Integer client_id;

    public clientDetailPost(Integer client_id) {
        this.client_id = client_id;
    }

    public Integer getClient_id() {
        return client_id;
    }

    public void setClient_id(Integer client_id) {
        this.client_id = client_id;
    }
}
