package com.styleappteam.styleapp.fragments.fragments_principal;
import com.styleappteam.styleapp.adapters.Type_Adapter;
import com.styleappteam.styleapp.connection_service.styleapp_API;
import com.styleappteam.styleapp.R;
import com.styleappteam.styleapp.model.Type;

/**
 * Created by eduardo on 1/5/17.
 */

import android.app.ProgressDialog;
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
import static com.styleappteam.styleapp.VariablesGlobales.currentType;

public class Principal extends Fragment {

    public Principal() {
        // Required empty public constructor
    }
    private ArrayList<Type> tipos=null;
    private Type_Adapter adapter1;
    private ProgressDialog progress;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.principal, container, false);
        ListView rootView= (ListView) view.findViewById(R.id.list);

        progress = new ProgressDialog(getActivity());
        progress.setMessage(getResources().getString(R.string.loading));
        progress.setCancelable(false);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();

        adapter1=new Type_Adapter(getActivity(), R.layout.basic_list);

        rootView.setAdapter(adapter1);

        rootView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)  {
                Fragment fragment = new PrincipalSecondFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, fragment)
                        .addToBackStack(null)
                        .commit();
                currentType=tipos.get(position);
            }
        });

        conexion.retrofitLoad();

        if(conexion.getRetrofit()!=null){
            obtenerDatos(conexion.getRetrofit());
        }else{
            Toast.makeText(getContext(), getResources().getString(R.string.connection_error), Toast.LENGTH_SHORT).show();
            progress.dismiss();
        }

        return view;
    }
    private void obtenerDatos(Retrofit retrofit) {
        styleapp_API service = retrofit.create(styleapp_API.class);
        Call<ArrayList<Type>> typeCall = service.obtenerlistaTipos();

        typeCall.enqueue(new Callback<ArrayList<Type>>() {
            @Override
            public void onResponse(Call<ArrayList<Type>> call, Response<ArrayList<Type>> response) {
                if (response.isSuccessful()) {
                    tipos = response.body();
                    adapter1.addAll(tipos);
                    progress.dismiss();
                } else {
                    progress.dismiss();
                    Toast.makeText(getContext(), "Error de Conexión", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Type>> call, Throwable t) {
                Log.e(TAG, " onFailure: " + t.getMessage());
                Toast.makeText(getContext(), "Error de Conexión", Toast.LENGTH_SHORT).show();
                progress.dismiss();
            }
        });
    }

}