package com.styleappteam.styleapp.fragments;
import com.styleappteam.styleapp.classes.*;
import com.styleappteam.styleapp.WorkerList;
import com.styleappteam.styleapp.R;

/**
 * Created by eduardo on 1/5/17.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.AdapterView;
import android.content.Intent;

public class Principal extends Fragment {

    public Principal() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.principal, container, false);
        ListView rootView= (ListView) view.findViewById(R.id.list);

        service_adapter adapter1=new service_adapter(getActivity(), R.layout.basic_list);
        for(int i=0;i<40;i++)
            adapter1.add(new Service("Corte de cabello","Realizado", R.drawable.corte_mujer));

        rootView.setAdapter(adapter1);
      rootView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> parent, View view, int position, long id)  {
                startActivity(new Intent(getActivity(), WorkerList.class));
            }
        });
        return view;
    }
}