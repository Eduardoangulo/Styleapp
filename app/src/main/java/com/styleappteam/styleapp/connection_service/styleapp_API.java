package com.styleappteam.styleapp.connection_service;

import com.styleappteam.styleapp.classes.Type_Service;
import com.styleappteam.styleapp.classes.Worker;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Luis on 14/06/2017.
 */

public interface styleapp_API {
    @GET("types")
    Call<ArrayList<Type_Service>> obtenerlistaTipos();

    @Headers({ "Content-Type: application/json"})
    @POST("clients")
    Call<NewUser> registrarCliente(@Body NewUser newuser);

    @Headers({ "Content-Type: application/json"})
    @POST("clients/login")
    Call<loginResult> login(@Body loginPost log);

    @Headers({ "Content-Type: application/json"})
    @POST("clients/getWorkers")
    Call<ArrayList<Worker>> obtenerWorkers(@Body GetWorkers infoWorker);
}
