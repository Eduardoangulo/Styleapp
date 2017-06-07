package com.styleappteam.styleapp.fragments;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.styleappteam.styleapp.*;

public class Miperfil extends Fragment {

    public Miperfil() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //FrameLayout frame = (FrameLayout)getView().findViewById(R.id.frame_inicial);
        //frame.setVisibility(View.GONE);
        return inflater.inflate(R.layout.miperfil, container, false);
    }
}