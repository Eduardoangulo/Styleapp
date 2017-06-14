package com.styleappteam.styleapp.ConexionService;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.styleappteam.styleapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;
import static java.security.AccessController.getContext;

/**
 * Created by Luis on 14/06/2017.
 */

public class api_connection {

    private Context context;
    private Retrofit retrofit;
    private String url;
    private final String TAG;

    public api_connection(Context context,final String TAG, String url){
        this.context=context;
        this.url=url;
        this.TAG=TAG;
    }

    public void retrofitLoad(){
        Log.i(TAG, "Entro a retrofit");
        if(isOnline()){
            Log.i(TAG, "Hay internet");
            setRetrofit(new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build());
            Log.i(TAG, "creo retrofit");
        }
        else{
            Toast.makeText(context, context.getResources().getString(R.string.noInternet) , Toast.LENGTH_SHORT).show();
        }

    }
    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public void setRetrofit(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
