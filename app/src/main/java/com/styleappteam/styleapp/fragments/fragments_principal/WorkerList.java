package com.styleappteam.styleapp.fragments.fragments_principal;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import android.Manifest;
import com.styleappteam.styleapp.R;
import com.styleappteam.styleapp.activities.MapActivity;
import com.styleappteam.styleapp.connection_service.GetWorkers;
import com.styleappteam.styleapp.connection_service.InfoWorker;
import com.styleappteam.styleapp.model.Worker;
import com.styleappteam.styleapp.adapters.Worker_Adapter;
import com.styleappteam.styleapp.connection_service.styleapp_API;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.Context.LOCATION_SERVICE;
import static com.styleappteam.styleapp.VariablesGlobales.MY_PERMISSIONS_REQUEST_LOCATION;
import static com.styleappteam.styleapp.VariablesGlobales.TAG;
import static com.styleappteam.styleapp.VariablesGlobales.conexion;
import static com.styleappteam.styleapp.VariablesGlobales.currentService;
import static com.styleappteam.styleapp.VariablesGlobales.currentWorker;
import static com.styleappteam.styleapp.VariablesGlobales.infoWorker;
import static com.styleappteam.styleapp.VariablesGlobales.place_global;

/**
 * Created by Luis on 06/06/2017.
 */

public class WorkerList extends Fragment{
    private GetWorkers getWorkers;
    private ArrayList<Worker> workers;
    private Worker_Adapter adapter1;
    private SwipeRefreshLayout refresh;
    private ProgressDialog progress;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.workers, container, false);

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

        ListView rootView= (ListView) view.findViewById(R.id.list);

        adapter1=new Worker_Adapter(getActivity(), R.layout.worker_list);

        conexion.retrofitLoad();
        if(conexion.getRetrofit()!=null){
            Log.i(TAG, "Principal: Hay internet");
            if(checkLocationPermission()){
                if(place_global!=null)
                {
                    Toast.makeText(getActivity(),"La ubicación actual es: "+ place_global.getAddress(), Toast.LENGTH_SHORT).show();
                    infoWorker.setLatitude(place_global.getLatLng().latitude);
                    infoWorker.setLongitude(place_global.getLatLng().longitude);
                }
                else
                {
                    if(getLocation()[0]==-1.0){
                        Toast.makeText(getContext(),"error: Se utilizará su ubicación predeterminada",Toast.LENGTH_SHORT).show();
                        infoWorker.setLatitude(-12.054227); //jalar del registro
                        infoWorker.setLongitude(-77.082802); //jalar del registro
                    }else{
                        Log.i(TAG, "Se utilizará su ubicación: "+getLocation()[0]+" "+getLocation()[1]);
                        Toast.makeText(getContext(),"Se utilizará su ubicación",Toast.LENGTH_SHORT).show();
                        infoWorker.setLatitude(getLocation()[0]); //jalar del registro
                        infoWorker.setLongitude(getLocation()[1]); //jalar del registro
                    }
                }
            }
            obtenerDatosWorkers(conexion.getRetrofit());
        }else
        {
            progress.hide();
            Log.e(TAG, "Principal: se fue el internet");
        }

        rootView.setAdapter(adapter1);
        rootView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> parent, View view, int position, long id)  {

                if(workers==null){
                    view.setClickable(false);
                }
                else{

                    Fragment fragment = new Pago();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content_frame, fragment)
                            .addToBackStack(null)
                            .commit();
                    currentWorker = workers.get(position);
                }
            }
        });

        FloatingActionButton myFab = (FloatingActionButton) view.findViewById(R.id.fab_maps);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MapActivity.class));
            }
        });

        return view;
    }



    private void refreshContent(){
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                conexion.retrofitLoad();
                if(conexion.getRetrofit()!=null){
                    Log.i(TAG, "Principal: Hay internet");
                    obtenerDatosWorkers(conexion.getRetrofit());
                }else
                {
                    Log.e(TAG, "Principal: se fue el internet");
                }
                refresh.setRefreshing(false);
            }
        },1500);
    }
    private void obtenerDatosWorkers(Retrofit retrofit) {
        Log.i(TAG, "obtener datos");

        if(workers!=null){
            workers.clear();
            adapter1.clear();
        }

        getWorkers=new GetWorkers();

        styleapp_API service = retrofit.create(styleapp_API.class);

        if(place_global!=null)
        {
            Toast.makeText(getActivity(),"La ubicación actual es: "+ place_global.getAddress(), Toast.LENGTH_SHORT).show();
            infoWorker.setLatitude(place_global.getLatLng().latitude);
            infoWorker.setLongitude(place_global.getLatLng().longitude);
        }

        infoWorker.setService_id(currentService.getId());

        Call<GetWorkers> workerCall = service.obtenerWorkers(infoWorker);

        workerCall.enqueue(new Callback<GetWorkers>() {
            @Override
            public void onResponse(Call<GetWorkers> call, Response<GetWorkers> response) {
                if (response.isSuccessful()) {
                    getWorkers = response.body();
                    workers = (ArrayList) getWorkers.getWorkers();
                    adapter1.addAll(workers);
                    progress.dismiss();

                } else {
                    progress.dismiss();
                    Toast.makeText(getContext(), getResources().getString(R.string.connection_error), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, " onResponse: " + response.errorBody());
                }

            }
            @Override
            public void onFailure(Call<GetWorkers> call, Throwable t) {
                Log.e(TAG, " onFailure: " + t.getMessage());
                Toast.makeText(getContext(), getResources().getString(R.string.connection_error), Toast.LENGTH_SHORT).show();
                progress.dismiss();
            }
        });
    }

    private boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission. ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission. ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(getActivity())
                        .setTitle("Styleapp necesita tu ubicación!")
                        .setMessage("Activar la ubicacion ayuda a encontrar a los estilistas mas cercanos a ti")
                        .setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        }else{
            return true;
        }
    }
    private double[] getLocation(){
        double coordenadas[]= new double[2];
        // Get the location manager
        LocationManager locationManager = (LocationManager)
                getActivity().getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, false);
        try{
            Location location = locationManager.getLastKnownLocation(bestProvider);
            try {
                coordenadas[0] = location.getLatitude();
                coordenadas[1]  = location.getLongitude();
            } catch (NullPointerException e) {
                coordenadas[0] = -1.0;
                coordenadas[1]  = -1.0;
            }
        }
        catch(SecurityException e){
            Log.e(TAG, "No esta autorizado");
            coordenadas[0] = -1.0;
            coordenadas[1]  = -1.0;
        }
        return coordenadas;
    }

}
