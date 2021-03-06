package com.styleappteam.styleapp.connection_service;

import com.styleappteam.styleapp.connection_service.detail_creation.DetailPost;
import com.styleappteam.styleapp.connection_service.detail_creation.DetailPostResponse;
import com.styleappteam.styleapp.connection_service.detail_creation.RatingPost;
import com.styleappteam.styleapp.connection_service.detail_creation.RatingResult;
import com.styleappteam.styleapp.connection_service.login.loginPost;
import com.styleappteam.styleapp.connection_service.login.loginResult;
import com.styleappteam.styleapp.connection_service.status.StatusPost;
import com.styleappteam.styleapp.connection_service.status.StatusResponse;
import com.styleappteam.styleapp.model.Services;
import com.styleappteam.styleapp.model.Type;

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
    @Headers({ "Content-Type: application/json"})
    @GET("types")
    Call<ArrayList<Type>> obtenerlistaTipos();

    @Headers({ "Content-Type: application/json"})
    @GET("services")
    Call<ArrayList<Services>> obtenerListaServicios();

    @Headers({ "Content-Type: application/json"})
    @POST("clients")
    Call<NewUser> registrarCliente(@Body NewUser newuser);

    @Headers({ "Content-Type: application/json"})
    @POST("clients/login")
    Call<loginResult> login(@Body loginPost log);

    @Headers({ "Content-Type: application/json"})
    @POST("clients/getWorkers")
    Call<GetWorkers> obtenerWorkers(@Body InfoWorker infoWorker);

    @Headers({ "Content-Type: application/json"})
    @POST("details/client")
    Call<ArrayList<DetailClient>> obtenerDetailClient(@Body clientDetailPost cpost);

    @Headers({ "Content-Type: application/json"})
    @POST("details")
    Call<TokenToServer> enviarToken(@Body TokenToServer tokenToServer);

    @Headers({ "Content-Type: application/json"})
    @POST("details")
    Call<DetailPostResponse> creatDetalle(@Body DetailPost detailPost);

    @Headers({ "Content-Type: application/json"})
    @POST("details/changeValue")
    Call<RatingResult> valorar(@Body RatingPost ratingPost);
    @Headers({ "Content-Type: application/json"})
    @POST("details/done")
    Call<StatusResponse> doneService(@Body StatusPost statusPost);
}
