package com.styleappteam.styleapp.fragments.fragments_mis_servicios;

/**
 * Created by eduardo on 1/5/17.
 */
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.styleappteam.styleapp.*;
import com.styleappteam.styleapp.adapters.misservicios_Adapter;
import com.styleappteam.styleapp.connection_service.DetailClient;
import com.styleappteam.styleapp.connection_service.clientDetailPost;
import com.styleappteam.styleapp.connection_service.detail_creation.RatingPost;
import com.styleappteam.styleapp.connection_service.detail_creation.RatingResult;
import com.styleappteam.styleapp.connection_service.status.StatusPost;
import com.styleappteam.styleapp.connection_service.status.StatusResponse;
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
        rootView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if(detailClients.get(position).getValue()==null) {
                switch (detailClients.get(position).getStatus()) {
                    case 1:
                        rateDetail(detailClients.get(position).getId());
                        break;
                    case 2:
                        doneDetail(detailClients.get(position).getId());
                        break;
                    default:
                        break;
                }
            }
            else{
                Toast.makeText(getContext(), "Ya ha valorado este servicio", Toast.LENGTH_SHORT).show();
            }
            }
        });

        conexion.retrofitLoad();

        if (conexion.getRetrofit() != null) {
            obtenerDatos(conexion.getRetrofit());
        }

        return view;
    }
    private void rateDetail(final int id){
        RatingDialog dialog= new RatingDialog();
        dialog.setRatingDialogListener(new RatingDialog.RatingDialogListener() {
            @Override
            public void onDialogPositiveClick(DialogFragment dialog, float rating) {
            Toast.makeText(getContext(), "Valoracion: "+rating, Toast.LENGTH_SHORT).show();
            conexion.retrofitLoad();
            if(conexion.getRetrofit()!=null){
              Log.i(TAG, "Principal: Hay internet");
                styleapp_API service = conexion.getRetrofit().create(styleapp_API.class);
                Call<RatingResult> Call = service.valorar(new RatingPost(id, Math.round(rating)));
                Call.enqueue(new Callback<RatingResult>() {
                    @Override
                    public void onResponse(Call<RatingResult> call, Response<RatingResult> response) {
                        Log.i(TAG, "Se valoro exitosamente");
                    }
                    @Override
                    public void onFailure(Call<RatingResult> call, Throwable t) {
                        Log.e(TAG,"NO se pudo valorar");
                        Toast.makeText(getContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
                    }
                });

            }else {
              Log.e(TAG, "Principal: se fue el internet");
            }

            }
        });
        dialog.show(getFragmentManager() , "RatingDialog");

    }
    private void doneDetail(final int id){
        DoneDialog dialog= new DoneDialog();
        dialog.setDoneDialogListener(new DoneDialog.DoneDialogListener(){
            @Override
            public void onDialogPositiveClick(DialogFragment dialog) {
                conexion.retrofitLoad();
                if(conexion.getRetrofit()!=null){
                    Log.i(TAG, "Principal: Hay internet");
                    styleapp_API service = conexion.getRetrofit().create(styleapp_API.class);
                    Call<StatusResponse> Call = service.doneService(new StatusPost(id));
                    Call.enqueue(new Callback<StatusResponse>() {
                        @Override
                        public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                            refreshContent();
                            Log.i(TAG, "Se marco como realizado exitosamente");
                        }
                        @Override
                        public void onFailure(Call<StatusResponse> call, Throwable t) {
                            Log.e(TAG,"NO se pudo marcar");
                            Toast.makeText(getContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
                        }
                    });

                }else {
                    Log.e(TAG, "Principal: se fue el internet");
                }

            }
        });
        dialog.show(getFragmentManager() , "DoneDialog");

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