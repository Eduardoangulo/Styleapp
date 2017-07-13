package com.styleappteam.styleapp.fragments.fragments_mis_servicios;

/**
 * Created by eduardo on 1/5/17.
 */
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.styleappteam.styleapp.*;
import com.styleappteam.styleapp.adapters.misservicios_Adapter;
import com.styleappteam.styleapp.connection_service.DetailClient;
import com.styleappteam.styleapp.connection_service.clientDetailPost;
import com.styleappteam.styleapp.connection_service.styleapp_API;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.styleappteam.styleapp.VariablesGlobales.TAG;
import static com.styleappteam.styleapp.VariablesGlobales.conexion;
import static com.styleappteam.styleapp.VariablesGlobales.currentClient;

public class Misservicios extends Fragment {
    private misservicios_Adapter adapter1;
    private ArrayList<DetailClient> detailClients= new ArrayList<>();
    private ProgressDialog progress;
    private SwipeRefreshLayout refresh;

    public Misservicios() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.misservicios, container, false);
        ListView rootView= (ListView) view.findViewById(R.id.list);
        progress = new ProgressDialog(getActivity());
        progress.setMessage(getResources().getString(R.string.loading));
        progress.setCancelable(false);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();

        refresh= (SwipeRefreshLayout)view.findViewById(R.id.swiperefresh);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });
        refresh.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));

        adapter1=new misservicios_Adapter(getActivity(), R.layout.myservices_list);
        rootView.setAdapter(adapter1);

        conexion.retrofitLoad();

        if (conexion.getRetrofit() != null) {
            obtenerDatos(conexion.getRetrofit());
        }

        return view;
    }
    private void refreshContent(){
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                conexion.retrofitLoad();
                if(conexion.getRetrofit()!=null){
                    Log.i(TAG, "Principal: Hay internet");
                    obtenerDatos(conexion.getRetrofit());
                }else
                {
                    Log.e(TAG, "Principal: se fue el internet");
                }
                refresh.setRefreshing(false);
            }
        },0);
    }
    private void obtenerDatos(Retrofit retrofit) {
        Log.i(TAG,"MIS SERVICIOS obtenerdatos");
        styleapp_API service = retrofit.create(styleapp_API.class);
        System.out.println(currentClient.getId());

        Call<ArrayList<DetailClient>> detailClientCall = service.obtenerDetailClient(new clientDetailPost(currentClient.getId()));

        detailClientCall.enqueue(new Callback<ArrayList<DetailClient>>() {
            @Override
            public void onResponse(Call<ArrayList<DetailClient>> call, Response<ArrayList<DetailClient>> response) {
                if (response.isSuccessful()) {
                    detailClients = response.body();
                    Log.i(TAG,"MIS SERVICIOS se obtuvo datos: "+response.body().size());
                    adapter1.clear();
                    Collections.reverse(detailClients);
                    adapter1.addAll(detailClients);
                } else {
                    Log.e(TAG,"MIS SERVICIOS no se obtuvo datos");
                    Toast.makeText(getContext(), getResources().getString(R.string.connection_error), Toast.LENGTH_SHORT).show();
                }
                progress.dismiss();
            }
            @Override
            public void onFailure(Call<ArrayList<DetailClient>> call, Throwable t) {
                Log.e(TAG, "MIS SERVICIOS onFailure: " + t.getMessage());
                progress.dismiss();
                Toast.makeText(getContext(), getResources().getString(R.string.connection_error), Toast.LENGTH_SHORT).show();
            }
        });
    }

}