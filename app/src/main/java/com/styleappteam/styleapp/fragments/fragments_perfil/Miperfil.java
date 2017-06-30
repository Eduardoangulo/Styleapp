package com.styleappteam.styleapp.fragments.fragments_perfil;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.styleappteam.styleapp.*;
import com.styleappteam.styleapp.activities.LoginActivity;


import static com.styleappteam.styleapp.VariablesGlobales.currentClient;

public class Miperfil extends Fragment {

    public Miperfil() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.miperfil, container, false);
        if (currentClient == null) {
            goLoginScreen();
        } else {
            displayProfileInfo(view);
        }
        return view;
    }

    private void displayProfileInfo(View view) {
                TextView nameTextView= (TextView) view.findViewById(R.id.profileName);
                TextView telfTextView= (TextView) view.findViewById(R.id.profilePhone);
                TextView emailTextView= (TextView) view.findViewById(R.id.profileEmail);
                ImageView profileImg = (ImageView) view.findViewById(R.id.ProfilePicture);
                nameTextView.setText(currentClient.getUser().getFirstName()+" "+currentClient.getUser().getLastName());
                emailTextView.setText(currentClient.getUser().getEmail());
                telfTextView.setText(currentClient.getUser().getTelephone().toString());
                Glide.with(getContext())
                    .load(currentClient.getPhoto())
                    .fitCenter()
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(profileImg);

    }
    private void goLoginScreen() {
        Intent intent= new Intent(getActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}