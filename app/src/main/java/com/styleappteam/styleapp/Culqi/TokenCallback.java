package com.styleappteam.styleapp.Culqi;

import org.json.JSONObject;

/**
 * Created by culqi on 2/7/17.
 */

public interface TokenCallback {

    public void onSuccess(JSONObject token);

    public void onError(Exception error);

}
