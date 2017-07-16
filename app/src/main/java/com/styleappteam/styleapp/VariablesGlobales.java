package com.styleappteam.styleapp;

import android.content.SharedPreferences;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.Marker;
import com.styleappteam.styleapp.connection_service.API_Connection;
import com.styleappteam.styleapp.connection_service.InfoWorker;
import com.styleappteam.styleapp.model.Client;
import com.styleappteam.styleapp.model.Services;
import com.styleappteam.styleapp.model.Type;
import com.styleappteam.styleapp.model.Worker;

/**
 * Created by eduardo on 6/11/17.
 */

public class VariablesGlobales {
    public static Marker marker_global;
    public static Place place_global;
    public static String URL_desarrollo = "http://styleapphome.prodequa.com/api/";
    public final static String TAG="STYLEAPPLOGS";
    public final static String keyNoti="keyNoti";
    public static API_Connection conexion;
    public static Client currentClient;
    public static Type currentType;
    public static Worker currentWorker;
    public static Services currentService;
    public static SharedPreferences loginPreferences;
    public static SharedPreferences.Editor loginPrefsEditor;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public static InfoWorker infoWorker = new InfoWorker();
}
