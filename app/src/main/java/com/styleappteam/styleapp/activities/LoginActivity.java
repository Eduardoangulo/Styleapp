package com.styleappteam.styleapp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.styleappteam.styleapp.R;
import com.styleappteam.styleapp.connection_service.loginPost;
import com.styleappteam.styleapp.connection_service.loginResult;
import com.styleappteam.styleapp.connection_service.styleapp_API;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.styleappteam.styleapp.VariablesGlobales.TAG;
import static com.styleappteam.styleapp.VariablesGlobales.conexion;
import static com.styleappteam.styleapp.VariablesGlobales.currentClient;

public class LoginActivity extends AppCompatActivity {
    private LoginButton loginButton;
    private CallbackManager callbackManager;

    private TextView registerBTN;
    private String username,password;
    private TextView regularLogin;
    private EditText login_user;
    private EditText login_password;

    private CheckBox saveLoginCheckBox;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        regularLogin= (TextView) findViewById(R.id.ingresarLogin);
        registerBTN= (TextView) findViewById(R.id.registerLogin) ;
        login_user= (EditText) findViewById(R.id.loginUser);
        login_password= (EditText) findViewById(R.id.loginPass);
        saveLoginCheckBox = (CheckBox)findViewById(R.id.saveLoginCheckBox);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin == true) {
            login_user.setText(loginPreferences.getString("username", ""));
            login_password.setText(loginPreferences.getString("password", ""));
            saveLoginCheckBox.setChecked(true);
        }

        registerBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

        regularLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(login_user.getWindowToken(), 0);

                username = login_user.getText().toString();
                password = login_password.getText().toString();

                if (saveLoginCheckBox.isChecked()) {
                    loginPrefsEditor.putBoolean("saveLogin", true);
                    loginPrefsEditor.putString("username", username);
                    loginPrefsEditor.putString("password", password);
                    loginPrefsEditor.commit();
                } else {
                    loginPrefsEditor.clear();
                    loginPrefsEditor.commit();
                }

                loginApi(login_user.getText().toString(), login_password.getText().toString());
            }
        });

        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.i(TAG, "Login con fb exito");
                signUp();
               //verifUser();
            }

            @Override
            public void onCancel() {
                //Toast.makeText(getApplicationContext(), "Regi", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(getApplicationContext(), "Error de conexion a Facebook", Toast.LENGTH_SHORT).show();
            }

        });

    }

    private void verifUser() {
        Log.i(TAG, "verifUser");
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                final JSONObject json = response.getJSONObject();
                try {
                    if (json != null) {
                        loginApi(json.getString("email"), "password");
                    } else {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.connection_error), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "email");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void loginApi(String email, String password){
        loginPost lPost = new loginPost(email, password);
        conexion.retrofitLoad();
        if(conexion.getRetrofit()!=null){
            Log.i(TAG, "Principal: Hay internet");
            styleapp_API service = conexion.getRetrofit().create(styleapp_API.class);
            Call<loginResult> Call = service.login(lPost);
            Call.enqueue(new Callback<loginResult>() {
                @Override
                public void onResponse(Call<loginResult> call, Response<loginResult> response) {
                    if (response.isSuccessful()) {
                        if(response.body().getSuccess()){
                            Log.i(TAG, "Usuario Correcto");
                            Toast.makeText(getApplicationContext(), "Bienvenido a Styleapp", Toast.LENGTH_SHORT).show();
                            currentClient=response.body().getClient();
                            goMainScreen();
                        }
                        else {
                            Log.i(TAG, "Usuario Incorrecto");
                            Toast.makeText(getApplicationContext(), "Usuario o Contraseña Incorrectos", Toast.LENGTH_SHORT).show();
                            //signUp();
                        }
                    }
                    else{
                        Log.e(TAG, " Verificar onResponse: " + response.errorBody());
                    }
                }
                @Override
                public void onFailure(Call<loginResult> call, Throwable t) {
                    Log.e(TAG, " Verificar onFailure: " + t.getMessage());
                }
            });
        }else {
            Log.e(TAG, "Principal: se fue el internet");
        }
    }

    private void goMainScreen(){
        Intent intent= new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void signUp(){
        Intent intent= new Intent(this, SignUpActivity.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(0,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
