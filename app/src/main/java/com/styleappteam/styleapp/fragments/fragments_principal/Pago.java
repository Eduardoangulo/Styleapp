package com.styleappteam.styleapp.fragments.fragments_principal;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.styleappteam.styleapp.Culqi.Card;
import com.styleappteam.styleapp.Culqi.Token;
import com.styleappteam.styleapp.Culqi.TokenCallback;
import com.styleappteam.styleapp.Culqi.Validation;
import com.styleappteam.styleapp.R;
import com.styleappteam.styleapp.connection_service.API_Connection;
import com.styleappteam.styleapp.connection_service.TokenToServer;
import com.styleappteam.styleapp.connection_service.detail_creation.DetailPost;
import com.styleappteam.styleapp.connection_service.detail_creation.DetailPostResponse;
import com.styleappteam.styleapp.connection_service.notifications.Datos;
import com.styleappteam.styleapp.connection_service.notifications.Notificacion;
import com.styleappteam.styleapp.connection_service.notifications.NotificationPost;
import com.styleappteam.styleapp.connection_service.notifications.NotificationResponse;
import com.styleappteam.styleapp.connection_service.notifications_API;
import com.styleappteam.styleapp.connection_service.styleapp_API;
import com.styleappteam.styleapp.model.Worker;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.styleappteam.styleapp.VariablesGlobales.TAG;
import static com.styleappteam.styleapp.VariablesGlobales.conexion;
import static com.styleappteam.styleapp.VariablesGlobales.currentClient;
import static com.styleappteam.styleapp.VariablesGlobales.currentService;
import static com.styleappteam.styleapp.VariablesGlobales.currentWorker;

/**
 * Created by eduardo on 6/25/17.
 */

public class Pago extends Fragment {
    Validation validation;

    ProgressDialog progress;

    TextView txtcardnumber, txtcvv, txtmonth, txtyear, txtemail, kind_card, result;
    Button btnPay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.payment, container, false);

        validation = new Validation();

        progress = new ProgressDialog(getActivity());
        progress.setMessage("Validando informacion de la tarjeta");
        progress.setCancelable(false);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        txtcardnumber = (TextView) view.findViewById(R.id.txt_cardnumber);

        txtcvv = (TextView) view.findViewById(R.id.txt_cvv);

        txtmonth = (TextView) view.findViewById(R.id.txt_month);

        txtyear = (TextView) view.findViewById(R.id.txt_year);

        txtemail = (TextView) view.findViewById(R.id.txt_email);

        kind_card = (TextView) view.findViewById(R.id.kind_card);

        result = (TextView) view.findViewById(R.id.token_id);

        btnPay = (Button) view.findViewById(R.id.btn_pay);

        txtcvv.setEnabled(false);

        txtcardnumber.setTransformationMethod(new NumericKeyBoardTransformationMethod());
        txtcvv.setTransformationMethod(new NumericKeyBoardTransformationMethod());
        txtmonth.setTransformationMethod(new NumericKeyBoardTransformationMethod());
        txtyear.setTransformationMethod(new NumericKeyBoardTransformationMethod());
        txtemail.setTransformationMethod(new NumericKeyBoardTransformationMethod());
        final API_Connection noti= new API_Connection(getContext(), TAG, "https://fcm.googleapis.com/");

        txtcardnumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == 0){
                    txtcvv.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = txtcardnumber.getText().toString();
                if(s.length() == 0) {
                    txtcardnumber.setBackgroundResource(R.drawable.border_error);
                }

                if(validation.luhn(text)) {
                    txtcardnumber.setBackgroundResource(R.drawable.border_sucess);
                } else {
                    txtcardnumber.setBackgroundResource(R.drawable.border_error);
                }

                int cvv = validation.bin(text, kind_card);
                if(cvv > 0) {
                    txtcvv.setFilters(new InputFilter[]{new InputFilter.LengthFilter(cvv)});
                    txtcvv.setEnabled(true);
                } else {
                    txtcvv.setEnabled(false);
                    txtcvv.setText("");
                }
            }
        });

        txtyear.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = txtyear.getText().toString();
                if(validation.year(text)){
                    txtyear.setBackgroundResource(R.drawable.border_error);
                } else {
                    txtyear.setBackgroundResource(R.drawable.border_sucess);
                }
            }
        });

        txtmonth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = txtmonth.getText().toString();
                if(validation.month(text)){
                    txtmonth.setBackgroundResource(R.drawable.border_error);
                } else {
                    txtmonth.setBackgroundResource(R.drawable.border_sucess);
                }
            }
        });

        btnPay.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                progress.show();
                noti.retrofitLoad();
                if(noti.getRetrofit()!=null){
                    Log.i(TAG, "Notificaciones: Hay internet");
                    enviarNotificacion(noti.getRetrofit(), currentWorker);
                }else
                {
                    Log.e(TAG, "Principal: se fue el internet");
                    progress.dismiss();
                }


               //CULQUI TARJETA DE CREDITO
               /* progress.show();

                Card card = new Card(txtcardnumber.getText().toString(), txtcvv.getText().toString(), Integer.parseInt(txtmonth.getText().toString()), Integer.parseInt(txtyear.getText().toString()), txtemail.getText().toString());

                Token token = new Token(getResources().getString(R.string.llave_publica));

                token.createToken(getApplicationContext(), card, new TokenCallback() {
                    @Override
                    public void onSuccess(JSONObject token) {
                        try {
                            result.setText(token.get("id").toString());
                        } catch (Exception ex){
                            progress.hide();
                        }
                        progress.hide();
                    }

                    @Override
                    public void onError(Exception error) {
                        progress.hide();
                    }
                });*/

            }
        });


        return view;
    }

    private void enviarNotificacion(Retrofit retrofit, Worker worker) {
        Log.i(TAG, "Enviar Notificacion");
        String clientName=currentClient.getUser().getFirstName()+" "+currentClient.getUser().getLastName();
        String token;
        if(worker.getToken()==null){
            token="gg";
        }
        else{
            token=worker.getToken();
        }
        NotificationPost nPost= new NotificationPost(token, new Notificacion("Nueva solicitud de servicio!",clientName+" solicito tus servicios!"), new Datos("Enviado deade app","Enviado deade app"));
        notifications_API service= retrofit.create(notifications_API.class);
        Call<NotificationResponse> Call= service.enviarNotificacion(nPost);
        Call.enqueue(new Callback<NotificationResponse>() {
            @Override
            public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
                if(response.isSuccessful()){
                    if(response.body().getSuccess()==1){
                        Log.i(TAG, "Se envio la notificacion");
                        conexion.retrofitLoad();
                        if(conexion.getRetrofit()!=null){
                            Log.i(TAG, "Principal: Hay internet");
                            crearDetalle(conexion.getRetrofit());
                        }else
                        {
                            Log.e(TAG, "Principal: se fue el internet");
                            progress.dismiss();
                        }
                    }
                    else{
                        Log.i(TAG, "Error al enviar notificacion");
                        Toast.makeText(getApplicationContext(), "Error al enviar solicitud", Toast.LENGTH_SHORT).show();
                        progress.dismiss();
                    }

                }
                else{
                    Log.e(TAG, " Notificacion-onResponse: " + response.errorBody());
                    Toast.makeText(getApplicationContext(), "Error al enviar solicitud", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<NotificationResponse> call, Throwable t) {
                Log.e(TAG, " Notificacion-onFailure: " + t.getMessage());
                progress.dismiss();
            }
        });
    }

    private void crearDetalle(Retrofit retrofit) {
        Log.i(TAG, currentWorker.getId()+" "+ currentClient.getId()+" "+currentService.getId());
        DetailPost detallePots= new DetailPost(currentWorker.getId(), currentClient.getId(), currentService.getId());
        styleapp_API service = retrofit.create(styleapp_API.class);
        Call<DetailPostResponse> Call = service.creatDetalle(detallePots);
        Call.enqueue(new Callback<DetailPostResponse>() {
            @Override
            public void onResponse(Call<DetailPostResponse> call, Response<DetailPostResponse> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Se envio su solicitud", Toast.LENGTH_SHORT).show();
                }
                else{
                    Log.e(TAG, " CrearDetalle-onResponse: " + response.errorBody());
                }
                progress.dismiss();
            }

            @Override
            public void onFailure(Call<DetailPostResponse> call, Throwable t) {
                Log.e(TAG, " CrearDetalle-onFailure: " + t.getMessage());
                progress.dismiss();
            }
        });
    }

    //enviarTokenAlServidor(token.get("id").toString());
    private class NumericKeyBoardTransformationMethod extends PasswordTransformationMethod {
        @Override
        public CharSequence getTransformation(CharSequence source, View view) {
            return source;
        }
    }
    private void enviarTokenAlServidor(String token){
        conexion.retrofitLoad();
        if(conexion!=null){
            TokenToServer tokenToServer = new TokenToServer();

            tokenToServer.setClient_id(currentClient.getId());
            tokenToServer.setServer_id(currentService.getId());
            tokenToServer.setWorker_id(currentWorker.getId());
            tokenToServer.setToken(token);

            enviarToken(conexion.getRetrofit(),tokenToServer);
        }

    }
    private void enviarToken(Retrofit retrofit, TokenToServer tokenToServer){
        styleapp_API service = retrofit.create(styleapp_API.class);
        Call<TokenToServer> workerCall = service.enviarToken(tokenToServer);
        workerCall.enqueue(new Callback<TokenToServer>() {
            @Override
            public void onResponse(Call<TokenToServer> call, Response<TokenToServer> response) {
                if (response.isSuccessful()) {

                }
            }
            @Override
            public void onFailure(Call<TokenToServer> call, Throwable t) {

            }
        });
    }
}
