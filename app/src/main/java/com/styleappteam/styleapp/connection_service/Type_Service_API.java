package com.styleappteam.styleapp.connection_service;

import com.styleappteam.styleapp.classes.Type_Service;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Luis on 14/06/2017.
 */

public interface Type_Service_API {
    @GET("types")
    Call<ArrayList<Type_Service>> obtenerlistaTipos();
}
