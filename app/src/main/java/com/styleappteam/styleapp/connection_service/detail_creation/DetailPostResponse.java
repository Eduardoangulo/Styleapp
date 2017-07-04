package com.styleappteam.styleapp.connection_service.detail_creation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Luis on 04/07/2017.
 */

public class DetailPostResponse {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("message2")
    @Expose
    private String message2;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage2() {
        return message2;
    }

    public void setMessage2(String message2) {
        this.message2 = message2;
    }
}
