package com.styleappteam.styleapp.activities;

import android.app.ProgressDialog;
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
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.iid.FirebaseInstanceId;
import com.styleappteam.styleapp.R;
import com.styleappteam.styleapp.connection_service.API_Connection;
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
import static com.styleappteam.styleapp.VariablesGlobales.URL_desarrollo;
import static com.styleappteam.styleapp.VariablesGlobales.conexion;
import static com.styleappteam.styleapp.VariablesGlobales.currentClient;
import static com.styleappteam.styleapp.VariablesGlobales.loginPreferences;
import static com.styleappteam.styleapp.VariablesGlobales.loginPrefsEditor;

public class LoginActivity extends AppCompatActivity {
    private LoginButton loginButton;
    private CallbackManager callbackManager;

    private TextView registerBTN;
    private String username,password;
    private TextView regularLogin;
    private EditText login_user;
    private EditText login_password;

    private CheckBox saveLoginCheckBox;

    private Boolean saveLogin;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progress = new ProgressDialog(this);
        progress.setMessage(getResources().getString(R.string.loading));
        progress.setCancelable(false);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        setContentView(R.layout.activity_login);
        regularLogin= (TextView) findViewById(R.id.ingresarLogin);
        registerBTN= (TextView) findViewById(R.id.registerLogin) ;
        login_user= (EditText) findViewById(R.id.loginUser);
        login_password= (EditText) findViewById(R.id.loginPass);
        saveLoginCheckBox = (CheckBox)findViewById(R.id.saveLoginCheckBox);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
        Log.i(TAG, "LoginActivity");
        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin) {
            progress.show();
            login_user.setText(loginPreferences.getString("username", ""));
            login_password.setText(loginPreferences.getString("password", ""));
            saveLoginCheckBox.setChecked(true);
            loginApi(login_user.getText().toString(), login_password.getText().toString());
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
                progress.show();
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
                Log.i(TAG, "register con fb exito");
                signUp();
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(getApplicationContext(), "Error de conexion a Facebook", Toast.LENGTH_SHORT).show();
            }

        });

    }


    private void loginApi(final String email, final String pass){
        Log.i(TAG, "User: "+email+" password: "+pass);
        loginPost lPost = new loginPost(email, pass, FirebaseInstanceId.getInstance().getToken());
        if(conexion==null){
            conexion= new API_Connection(getApplicationContext(), TAG, URL_desarrollo);
        }
        conexion.retrofitLoad();
        if(conexion.getRetrofit()!=null){
            Log.i(TAG, "Principal: Hay internet");
            styleapp_API service = conexion.getRetrofit().create(styleapp_API.class);
            Call<loginResult> Call = service.login(lPost);
            Call.enqueue(new Callback<loginResult>() {
                @Override
                public void onResponse(Call<loginResult> call, Response<loginResult> response) {
                    progress.dismiss();
                    if (response.isSuccessful()) {

                        if(response.body().getSuccess()){
                            Log.i(TAG, "Usuario Correcto");
                            Toast.makeText(getApplicationContext(), "Bienvenido a Styleapp", Toast.LENGTH_SHORT).show();
                            currentClient=response.body().getClient();
                            currentClient.setLogedUsername(email);
                            currentClient.setLogedPassword(pass);
                            goMainScreen();
                        }
                        else {
                            Log.i(TAG, "Usuario Incorrecto");

                            Toast.makeText(getApplicationContext(), "Usuario o Contraseña Incorrectos", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.connection_error), Toast.LENGTH_LONG).show();
                        Log.e(TAG, " Verificar onResponse: " + response.errorBody());
                    }

                }
                @Override
                public void onFailure(Call<loginResult> call, Throwable t) {
                    Log.e(TAG, " Verificar onFailure: " + t.getMessage());
                    progress.dismiss();
                }
            });
        }else {
            progress.dismiss();
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
