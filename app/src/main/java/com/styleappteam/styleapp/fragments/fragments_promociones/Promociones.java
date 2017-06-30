package com.styleappteam.styleapp.fragments.fragments_promociones;
import com.styleappteam.styleapp.*;
import com.styleappteam.styleapp.classes.classes_promociones.Coupon;
import com.styleappteam.styleapp.classes.classes_promociones.Coupons_Adapter;

/**
 * Created by eduardo on 1/5/17.
 */

import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

public class Promociones extends Fragment{

    public Promociones() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.promociones, container, false);
        FloatingActionButton addCoupon=(FloatingActionButton) view.findViewById(R.id.fab);
        addCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCouponDialog dialog= new addCouponDialog();
                dialog.setmListener(new addCouponDialog.addCouponDialogListener() {
                    @Override
                    public void onDialogPositiveClick(DialogFragment dialog, String result) {
                        Toast.makeText(getContext(), "Se añadio cupon: "+result, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onDialogNegativeClick(DialogFragment dialog) {

                    }
                });
                dialog.show(getFragmentManager() , "addCouponDialog");

            }
        });
        ListView rootView= (ListView) view.findViewById(R.id.list);

        Coupons_Adapter adapter1=new Coupons_Adapter(getActivity(), R.layout.basic_list);
        for(int i=0;i<4;i++)
            adapter1.add(new Coupon("Cortes de hombre", R.drawable.percent_50));

        rootView.setAdapter(adapter1);
        return view;
    }
}