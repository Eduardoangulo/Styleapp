package com.styleappteam.styleapp.fragments_main.fragments_principal;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.styleappteam.styleapp.R;
import com.styleappteam.styleapp.activities.MapActivity;
import com.styleappteam.styleapp.classes.Worker;
import com.styleappteam.styleapp.adapters.Worker_Adapter;
import static com.styleappteam.styleapp.VariablesGlobales.place_global;

/**
 * Created by Luis on 06/06/2017.
 */

public class WorkerList extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.workers, container, false);

        ListView rootView= (ListView) view.findViewById(R.id.list);
        Worker_Adapter adapter1=new Worker_Adapter(getActivity(), R.layout.worker_list);
        for(int i=0;i<40;i++)
            adapter1.add(new Worker("Eduardo Angulo", R.drawable.stars, R.drawable.user));

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
}
