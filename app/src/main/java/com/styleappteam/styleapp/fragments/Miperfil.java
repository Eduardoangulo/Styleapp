package com.styleappteam.styleapp.fragments;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.widget.ProfilePictureView;
import com.styleappteam.styleapp.*;

import org.json.JSONException;
import org.json.JSONObject;

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
        View view= inflater.inflate(R.layout.miperfil, container, false);
        if (AccessToken.getCurrentAccessToken() == null) {
            goLoginScreen();
        } else {
            RequestData();
                /*Profile profile = Profile.getCurrentProfile();
                if (profile != null) {
                   displayProfileInfo(profile, view);

                } else {
                    Profile.fetchProfileForCurrentAccessToken();
                }*/
        }
        return view;
    }

    private void RequestData() {
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object,GraphResponse response) {
                final JSONObject json = response.getJSONObject();
                displayProfileInfo(json, getView());
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,email,picture");
        request.setParameters(parameters);
        request.executeAsync();
    }
    private void displayProfileInfo(JSONObject json, View view) {
        try {
            if(json != null){
                TextView nameTextView= (TextView) view.findViewById(R.id.profileName);
                TextView emailTextView= (TextView) view.findViewById(R.id.profileEmail);
                nameTextView.setText(json.getString("name"));
                emailTextView.setText(json.getString("email"));
                ProfilePictureView profilePictureView= (ProfilePictureView) view.findViewById(R.id.ProfilePicture);
                profilePictureView.setProfileId(json.getString("id"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void goLoginScreen() {
        Intent intent= new Intent(getActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}