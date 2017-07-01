package com.styleappteam.styleapp;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.Marker;
import com.styleappteam.styleapp.connection_service.API_Connection;
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
    public static API_Connection conexion;
    public static Client currentClient;
    public static Type currentType;
    public static Worker currentWorker;
    public static Services currentService;
}
