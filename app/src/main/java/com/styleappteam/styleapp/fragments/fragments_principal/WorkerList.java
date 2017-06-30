package com.styleappteam.styleapp.fragments.fragments_principal;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.styleappteam.styleapp.R;
import com.styleappteam.styleapp.activities.MapActivity;
import com.styleappteam.styleapp.connection_service.GetWorkers;
import com.styleappteam.styleapp.model.Worker;
import com.styleappteam.styleapp.adapters.Worker_Adapter;
import com.styleappteam.styleapp.connection_service.styleapp_API;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.styleappteam.styleapp.VariablesGlobales.TAG;
import static com.styleappteam.styleapp.VariablesGlobales.conexion;
import static com.styleappteam.styleapp.VariablesGlobales.currentClient;
import static com.styleappteam.styleapp.VariablesGlobales.place_global;

/**
 * Created by Luis on 06/06/2017.
 */

public class WorkerList extends Fragment {
    private ArrayList<Worker> workers=null;
    private Worker_Adapter adapter1;
    private String [] place_local;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.workers, container, false);

        ListView rootView= (ListView) view.findViewById(R.id.list);

        adapter1=new Worker_Adapter(getActivity(), R.layout.worker_list);

        conexion.retrofitLoad();
        if(conexion.getRetrofit()!=null){
            Log.i(TAG, "Principal: Hay internet");
            //obtenerDatosWorkers(conexion.getRetrofit());
        }else
        {
            Log.e(TAG, "Principal: se fue el internet");
        }

        rootView.setAdapter(adapter1);
        rootView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> parent, View view, int position, long id)  {

                if(place_global==null){
                    view.setClickable(false);
                }
                else{
                    Fragment fragment = new Pago();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content_frame, fragment)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });

        FloatingActionButton myFab = (FloatingActionButton) view.findViewById(R.id.fab_maps);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MapActivity.class));
            }
        });


        if(place_global!=null)
        {
            Toast.makeText(getActivity(),"La ubicación actual es: "+ place_global.getAddress(), Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getActivity(),"No se tiene información de la dirección",Toast.LENGTH_SHORT).show();
        }
        return view;
    }

    private void obtenerDatosWorkers(Retrofit retrofit) {
        Log.i(TAG, "obtener datos");
        styleapp_API service = retrofit.create(styleapp_API.class);
        GetWorkers infoWorker = new GetWorkers();

        infoWorker.setDistrict_name("Santiago de Surco");
        infoWorker.setLatitude(25.3131225);
        infoWorker.setLongitude(25.3131224);
        //infoWorker.setLatitude(currentClient.getAddresses().get(1).getLatitude());
        //infoWorker.setLongitude(currentClient.getAddresses().get(1).getLongitude());

        Call<ArrayList<Worker>> workerCall = service.obtenerWorkers(infoWorker);

        workerCall.enqueue(new Callback<ArrayList<Worker>>() {
            @Override
            public void onResponse(Call<ArrayList<Worker>> call, Response<ArrayList<Worker>> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "Cargo la API");
                    workers = response.body();
                    for(int i=0; i<workers.size(); i++){
                        Log.i(TAG, " Nombre tipo: " +workers.get(i).getId());
                    }
                    adapter1.addAll(workers);
                    Log.i(TAG, "Se añadieron al ListView");

                } else {
                    Toast.makeText(getContext(), getResources().getString(R.string.connection_error), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, " onResponse: " + response.errorBody());
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Worker>> call, Throwable t) {
                Log.e(TAG, " onFailure: " + t.getMessage());
                Toast.makeText(getContext(), getResources().getString(R.string.connection_error), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
