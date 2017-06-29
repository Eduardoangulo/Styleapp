package com.styleappteam.styleapp.connection_service;

import com.styleappteam.styleapp.classes.Client;
import com.styleappteam.styleapp.classes.NewUser;
import com.styleappteam.styleapp.classes.Type_Service;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Luis on 14/06/2017.
 */

public interface Type_Service_API {
    @GET("types")
    Call<ArrayList<Type_Service>> obtenerlistaTipos();


    @Headers({ "Content-Type: application/json"})
    @POST("clients")
    Call<NewUser> registrarCliente(@Body NewUser newuser);
}
