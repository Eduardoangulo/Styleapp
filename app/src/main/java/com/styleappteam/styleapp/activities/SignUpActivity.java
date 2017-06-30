package com.styleappteam.styleapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.styleappteam.styleapp.R;
import com.styleappteam.styleapp.connection_service.NewUser;
import com.styleappteam.styleapp.connection_service.styleapp_API;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.styleappteam.styleapp.VariablesGlobales.TAG;
import static com.styleappteam.styleapp.VariablesGlobales.conexion;

public class SignUpActivity extends AppCompatActivity {

    private EditText nameTextView;
    private EditText LnameTextView;
    private EditText emailTextView;
    private EditText password;
    private TextView register;
    private boolean validEmail=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Log.i(TAG, "Entro a registrar");
        nameTextView= (EditText) findViewById(R.id.registerName);
        LnameTextView= (EditText) findViewById(R.id.registerLName);
        emailTextView= (EditText) findViewById(R.id.registerEmail);
        password= (EditText) findViewById(R.id.registerPassword);
        register= (TextView) findViewById(R.id.registerBtn);

        final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        emailTextView.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String email=emailTextView.getText().toString().trim();
                if (!email.matches(emailPattern) || s.length() <= 0)
                {
                    Toast.makeText(getApplicationContext(),"Invalid email address",Toast.LENGTH_SHORT).show();
                    validEmail=false;
                }
                else{
                    validEmail=true;
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // other stuffs
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // other stuffs
            }
        });
        if(AccessToken.getCurrentAccessToken()!=null){
            RequestData();
        }
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewUser newUser= new NewUser(emailTextView.getText().toString(), nameTextView.getText().toString(),LnameTextView.getText().toString(),emailTextView.getText().toString(),password.getText().toString());
                if(validateNewUser(newUser)){
                    conexion.retrofitLoad();
                    if(conexion.getRetrofit()!=null){
                        Log.i(TAG, "Principal: Hay internet");
                        registerUser(conexion.getRetrofit(), newUser);
                    }else {
                        Log.e(TAG, "Principal: se fue el internet");
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"Datos Invalidos", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
    private void RequestData() {
        Log.i(TAG, "requestData");
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                final JSONObject json = response.getJSONObject();
                displayProfileInfo(json);
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,first_name,last_name,link,email");
        request.setParameters(parameters);
        request.executeAsync();
    }
    private void displayProfileInfo(JSONObject json) {
        try {
            if(json != null){
                nameTextView.setText(json.getString("first_name"));
                LnameTextView.setText(json.getString("last_name"));
                emailTextView.setText(json.getString("email"));
            }
            else{
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.connection_error), Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void goLoginScreen() {
        LoginManager.getInstance().logOut();
        finish();
        Intent intent= new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(0,0);
    }

    private void registerUser(Retrofit retrofit, NewUser newUser){
        styleapp_API service = retrofit.create(styleapp_API.class);
        Call<NewUser> call = service.registrarCliente(newUser);
        call.enqueue(new Callback<NewUser>() {
            @Override
            public void onResponse(Call<NewUser> call, Response<NewUser> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "Se añadio cliente");
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.registerSuccess), Toast.LENGTH_SHORT).show();
                    goLoginScreen();

                } else {
                    Log.e(TAG, " onResponse: " + response.errorBody());
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.registerFail), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NewUser> call, Throwable t) {
                Log.e(TAG, " onFailure: " + t.getMessage());

            }
        });
    }
    private boolean validateNewUser(NewUser newUser){
        return !(!validEmail||newUser.getUsername()==" "||newUser.getFirst_name()==" "||newUser.getLast_name()==" ");
    }
    @Override
    protected void onPause() {
        super.onPause();
        LoginManager.getInstance().logOut();
    }

    @Override
    protected void onStop() {
        super.onStop();
        LoginManager.getInstance().logOut();
    }

}
