package com.styleappteam.styleapp.fragments;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.AccessToken;
import com.facebook.Profile;
import com.facebook.login.widget.ProfilePictureView;
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
        View view= inflater.inflate(R.layout.miperfil, container, false);
        if (AccessToken.getCurrentAccessToken() == null) {
            goLoginScreen();
        } else {
                Profile profile = Profile.getCurrentProfile();
                if (profile != null) {
                   displayProfileInfo(profile, view);

                } else {
                    Profile.fetchProfileForCurrentAccessToken();
                }
        }
        return view;
    }
    private void displayProfileInfo(Profile profile, View view) {
        String name = profile.getName();
        String photoUrl = profile.getProfilePictureUri(100, 100).toString();
        TextView nameTextView= (TextView) view.findViewById(R.id.profileName);
        nameTextView.setText(name);
        ProfilePictureView profilePictureView= (ProfilePictureView) view.findViewById(R.id.ProfilePicture);
        profilePictureView.setProfileId(profile.getId());
    }
    private void goLoginScreen() {
        Intent intent= new Intent(getActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}