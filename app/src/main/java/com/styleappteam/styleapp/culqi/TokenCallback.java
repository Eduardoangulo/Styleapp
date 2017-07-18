package com.styleappteam.styleapp.culqi;

import org.json.JSONException;
import org.json.JSONObject;


public interface TokenCallback {

    public void onSuccess(JSONObject token) throws JSONException;

    public void onError(Exception error);

}
