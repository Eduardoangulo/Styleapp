package com.styleappteam.styleapp.fragments;
import com.styleappteam.styleapp.ConexionService.type_serviceAPI;
import com.styleappteam.styleapp.classes.*;
import com.styleappteam.styleapp.WorkerList;
import com.styleappteam.styleapp.R;

/**
 * Created by eduardo on 1/5/17.
 */

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.AdapterView;
import android.content.Intent;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Principal extends Fragment {

    public Principal() {
        // Required empty public constructor
    }

    private Retrofit retrofit;
    private ArrayList<type_service> tipos_servicio=null;
    private final String TAG= "SERVICIOS";
    private typeAdapter adapter1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.principal, container, false);
        ListView rootView= (ListView) view.findViewById(R.id.list);
        adapter1=new typeAdapter(getActivity(), R.layout.basic_list);
        rootView.setAdapter(adapter1);
        rootView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)  {
                startActivity(new Intent(getActivity(), WorkerList.class));
            }
        });
        retrofitLoad("http://styleapphome.prodequa.com/api/types/");
        return view;
    }
    private void retrofitLoad(String url){
        Log.i(TAG, "Entro a retrofit");
        if(isOnline()){
            Log.i(TAG, "Hay internet");
            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            Log.i(TAG, "creo retrofit");
            obtenerDatos();
        }
        else{
            Toast.makeText(getContext(), getResources().getString(R.string.noInternet), Toast.LENGTH_SHORT).show();
        }

    }
    private void obtenerDatos() {
        Log.i(TAG, "obtener datos");
        type_serviceAPI service = retrofit.create(type_serviceAPI.class);
        Call<ArrayList<type_service>> pokemonRespuestaCall = service.obtenerlistaTipos();

        pokemonRespuestaCall.enqueue(new Callback<ArrayList<type_service>>() {
            @Override
            public void onResponse(Call<ArrayList<type_service>> call, Response<ArrayList<type_service>> response) {
                if (response.isSuccessful()) {
                    //aca asigna lo cojido al array
                    Log.i(TAG, "Cargo la API");
                    tipos_servicio = response.body();
                    for(int i=0; i<tipos_servicio.size(); i++){
                        Log.i(TAG, " Nombre tipo: " +tipos_servicio.get(i).getName());
                    }
                    adapter1.addAll(tipos_servicio);
                    Log.i(TAG, "Se añadieron al ListView");

                } else {
                    Toast.makeText(getContext(), getResources().getString(R.string.connection_error), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, " onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<type_service>> call, Throwable t) {
                Log.e(TAG, " onFailure: " + t.getMessage());
                Toast.makeText(getContext(), getResources().getString(R.string.connection_error), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }
}