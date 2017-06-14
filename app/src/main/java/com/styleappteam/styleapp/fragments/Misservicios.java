package com.styleappteam.styleapp.fragments;

/**
 * Created by eduardo on 1/5/17.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.styleappteam.styleapp.*;
import com.styleappteam.styleapp.classes.instancedService;
import com.styleappteam.styleapp.classes.instanced_service_adapter;

public class Misservicios extends Fragment {

    public Misservicios() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.misservicios, container, false);
        ListView rootView= (ListView) view.findViewById(R.id.list);

        instanced_service_adapter adapter1=new instanced_service_adapter(getActivity(), R.layout.myservices_list);
        for(int i=0;i<40;i++)
            adapter1.add(new instancedService("Corte de cabello","Cancelado", R.drawable.corte_hombre));

        rootView.setAdapter(adapter1);
        return view;
    }

}