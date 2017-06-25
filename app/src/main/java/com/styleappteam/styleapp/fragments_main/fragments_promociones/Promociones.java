package com.styleappteam.styleapp.fragments_main.fragments_promociones;
import com.styleappteam.styleapp.*;
import com.styleappteam.styleapp.classes.classes_promociones.Coupon;
import com.styleappteam.styleapp.classes.classes_promociones.Coupons_Adapter;

/**
 * Created by eduardo on 1/5/17.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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

        Coupons_Adapter adapter1=new Coupons_Adapter(getActivity(), R.layout.basic_list);
        for(int i=0;i<40;i++)
            adapter1.add(new Coupon("Cortes de hombre", R.drawable.percent_50));

        rootView.setAdapter(adapter1);
        return view;
    }
}