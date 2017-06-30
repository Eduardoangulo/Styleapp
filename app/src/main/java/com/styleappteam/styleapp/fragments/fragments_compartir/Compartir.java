package com.styleappteam.styleapp.fragments.fragments_compartir;
/**
 * Created by eduardo on 1/5/17.
 */
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.widget.Toast;

import com.styleappteam.styleapp.*;

public class Compartir extends Fragment {

    public Compartir() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.compartir, container, false);
        ImageView fb=(ImageView) view.findViewById(R.id.facebook);
        ImageView tw=(ImageView) view.findViewById(R.id.twitter);
        ImageView snap=(ImageView) view.findViewById(R.id.snapchat);
        ImageView inst=(ImageView) view.findViewById(R.id.instagram);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean Installed = appInstalledOrNot("com.facebook.katana");
                if(Installed){
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.setPackage("com.facebook.katana");
                    startActivity(intent);
                }else{
                    Toast.makeText(getActivity(), getResources().getString(R.string.app_not_found), Toast.LENGTH_SHORT).show();
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/Eduxaman95"));
                    startActivity(browserIntent);
                }

            }

        });
        tw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean Installed = appInstalledOrNot("com.twitter.android");
                if(Installed){
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.setPackage("com.twitter.android");
                    startActivity(intent);
                }else{
                    Toast.makeText(getActivity(), getResources().getString(R.string.app_not_found), Toast.LENGTH_SHORT).show();
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/Eduxaman95"));
                    startActivity(browserIntent);
                }

            }

        });
        snap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean Installed = appInstalledOrNot("com.snapchat.android");
                if(Installed){
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.setPackage("com.snapchat.android");
                    startActivity(intent);
                }else{
                    Toast.makeText(getActivity(), getResources().getString(R.string.app_not_found), Toast.LENGTH_SHORT).show();
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/Eduxaman95"));
                    startActivity(browserIntent);
                }

            }

        });
        inst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean Installed = appInstalledOrNot("com.instagram.android");
                if(Installed){
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.setPackage("com.instagram.android");
                    startActivity(intent);
                }else{
                    Toast.makeText(getActivity(), getResources().getString(R.string.app_not_found), Toast.LENGTH_SHORT).show();
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/Eduxaman95"));
                    startActivity(browserIntent);
                }

            }

        });
        return view;
    }
    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = getActivity().getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }

    }

}