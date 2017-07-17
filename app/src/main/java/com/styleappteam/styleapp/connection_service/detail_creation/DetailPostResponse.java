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
    @SerializedName("success")
    @Expose
    private boolean success;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
