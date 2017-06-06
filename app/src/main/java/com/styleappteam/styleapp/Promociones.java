package com.styleappteam.styleapp;
import com.styleappteam.styleapp.classes.*;

/**
 * Created by eduardo on 1/5/17.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.styleappteam.styleapp.classes.Service;
import com.styleappteam.styleapp.classes.service_adapter;

public class Promociones extends Fragment {

    public Promociones() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.promociones, container, false);
        ListView rootView= (ListView) view.findViewById(R.id.list);

        coupons_adapter adapter1=new coupons_adapter(getActivity(), R.layout.basic_list);
        for(int i=0;i<40;i++)
            adapter1.add(new Coupon("50% Descuento", R.mipmap.cover1));

        rootView.setAdapter(adapter1);
        return view;
    }
}