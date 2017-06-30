package com.styleappteam.styleapp.fragments.fragments_mis_servicios;

/**
 * Created by eduardo on 1/5/17.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.styleappteam.styleapp.*;
import com.styleappteam.styleapp.classes.classes_mis_servicios.Instanced_Service;
import com.styleappteam.styleapp.classes.classes_mis_servicios.Instanced_Service_Adapter;
import com.styleappteam.styleapp.connection_service.DetailClient;
import com.styleappteam.styleapp.connection_service.styleapp_API;
import com.styleappteam.styleapp.model.Type;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.styleappteam.styleapp.VariablesGlobales.TAG;
import static com.styleappteam.styleapp.VariablesGlobales.conexion;
import static com.styleappteam.styleapp.VariablesGlobales.currentClient;

public class Misservicios extends Fragment {
    private ArrayList<Instanced_Service> instanced_service;
    private Instanced_Service_Adapter adapter1;
    private ArrayList<DetailClient> detailClients;

    public Misservicios() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.misservicios, container, false);
        ListView rootView= (ListView) view.findViewById(R.id.list);
        adapter1=new Instanced_Service_Adapter(getActivity(), R.layout.myservices_list);
        rootView.setAdapter(adapter1);

        conexion.retrofitLoad();

        if (conexion.getRetrofit() != null) {
            obtenerDatos(conexion.getRetrofit());
        }

        return view;
    }
    private void obtenerDatos(Retrofit retrofit) {
        styleapp_API service = retrofit.create(styleapp_API.class);
        System.out.println(currentClient.getUserId());

        instanced_service = new ArrayList<>();

        Call<ArrayList<DetailClient>> detailClientCall = service.obtenerDetailClient(currentClient.getUserId());

        detailClientCall.enqueue(new Callback<ArrayList<DetailClient>>() {
            @Override
            public void onResponse(Call<ArrayList<DetailClient>> call, Response<ArrayList<DetailClient>> response) {
                if (response.isSuccessful()) {
                    detailClients = response.body();

                    for (int i = 0 ; i<detailClients.size();i++){
                        instanced_service.get(i).setServiceName(detailClients.get(i).getService().getName());
                        instanced_service.get(i).setState(detailClients.get(i).getStatus());
                    }
                        adapter1.addAll(instanced_service);

                } else {
                    Toast.makeText(getContext(), getResources().getString(R.string.connection_error), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<DetailClient>> call, Throwable t) {
                Log.e(TAG, " onFailure: " + t.getMessage());
                Toast.makeText(getContext(), getResources().getString(R.string.connection_error), Toast.LENGTH_SHORT).show();
            }
        });
    }

}