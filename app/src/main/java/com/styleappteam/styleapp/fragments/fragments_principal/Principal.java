package com.styleappteam.styleapp.fragments.fragments_principal;
import com.styleappteam.styleapp.adapters.Type_Adapter;
import com.styleappteam.styleapp.connection_service.styleapp_API;
import com.styleappteam.styleapp.R;
import com.styleappteam.styleapp.model.Type_Service;

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
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.styleappteam.styleapp.VariablesGlobales.TAG;
import static com.styleappteam.styleapp.VariablesGlobales.conexion;

public class Principal extends Fragment {

    public Principal() {
        // Required empty public constructor
    }
    private ArrayList<Type_Service> tipos_servicio=null;
    private Type_Adapter adapter1;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.principal, container, false);
        ListView rootView= (ListView) view.findViewById(R.id.list);
        adapter1=new Type_Adapter(getActivity(), R.layout.basic_list);
        rootView.setAdapter(adapter1);
        rootView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)  {
                Fragment fragment = new WorkerList();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        //API_Connection conexion= new API_Connection(getContext(), TAG, URL_desarrollo);
        conexion.retrofitLoad();
        if(conexion.getRetrofit()!=null){
            Log.i(TAG, "Principal: Hay internet");
            obtenerDatos(conexion.getRetrofit());
        }else
        {
            Log.e(TAG, "Principal: se fue el internet");
        }



        return view;
    }
    private void obtenerDatos(Retrofit retrofit) {
        Log.i(TAG, "obtener datos");
        styleapp_API service = retrofit.create(styleapp_API.class);
        Call<ArrayList<Type_Service>> typeCall = service.obtenerlistaTipos();

        typeCall.enqueue(new Callback<ArrayList<Type_Service>>() {
            @Override
            public void onResponse(Call<ArrayList<Type_Service>> call, Response<ArrayList<Type_Service>> response) {
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
            public void onFailure(Call<ArrayList<Type_Service>> call, Throwable t) {
                Log.e(TAG, " onFailure: " + t.getMessage());
                Toast.makeText(getContext(), getResources().getString(R.string.connection_error), Toast.LENGTH_SHORT).show();
            }
        });
    }

}