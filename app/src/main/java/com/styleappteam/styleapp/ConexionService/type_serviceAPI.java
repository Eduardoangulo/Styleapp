package com.styleappteam.styleapp.ConexionService;

import com.styleappteam.styleapp.classes.type_service;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Luis on 14/06/2017.
 */

public interface type_serviceAPI {
    @GET("?id=5")
    Call<ArrayList<type_service>> obtenerlistaTipos();
}
