package com.styleappteam.styleapp.fragments.fragments_principal;

import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.braintreepayments.cardform.view.CardForm;
import com.styleappteam.styleapp.R;
import com.styleappteam.styleapp.activities.PaymentConfirmed;
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
import com.styleappteam.styleapp.culqi.Card;
import com.styleappteam.styleapp.culqi.Token;
import com.styleappteam.styleapp.culqi.TokenCallback;
import com.styleappteam.styleapp.model.Worker;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.util.HashMap;
import java.util.Map;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.styleappteam.styleapp.VariablesGlobales.TAG;
import static com.styleappteam.styleapp.VariablesGlobales.conexion;
import static com.styleappteam.styleapp.VariablesGlobales.currentClient;
import static com.styleappteam.styleapp.VariablesGlobales.currentService;
import static com.styleappteam.styleapp.VariablesGlobales.currentWorker;
import static com.styleappteam.styleapp.VariablesGlobales.infoWorker;

/**
 * Created by eduardo on 6/25/17.
 */

public class Pago extends Fragment {
    //Validation validation;

    ProgressDialog progress;

    TextView txtcardnumber, txtcvv, txtmonth, txtyear, txtemail, kind_card, result;
    Button btnPay;

    private CardForm cardForm;
    private EditText emailEditText;
    private String email;

    private Integer cvv = null, m_exp = null, a_exp = null, productQuantity, convertedCulqiInt, publish_id, user_id, address_id;
    private Long numero = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.payment, container, false);
        progress = new ProgressDialog(getActivity());
        progress.setMessage(getResources().getString(R.string.loading));
        progress.setCancelable(false);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        cardForm = (CardForm) view.findViewById(R.id.card_form);
        cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .setup(getActivity());

        emailEditText = (EditText) view.findViewById(R.id.email_editTextView);

        cardForm.validate();

        //validation = new Validation();

        btnPay = (Button) view.findViewById(R.id.btn_pay);

        btnPay.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //progress.show();

                numero = Long.parseLong(cardForm.getCardNumber());
                cvv = Integer.parseInt(cardForm.getCvv());
                m_exp = Integer.parseInt(cardForm.getExpirationMonth());
                a_exp = Integer.parseInt(cardForm.getExpirationYear());
                email = emailEditText.getText().toString().trim();

                Card card = new Card(cardForm.getCardNumber(),
                        cardForm.getCvv(), m_exp,
                        a_exp, email);

                Log.d("culqi", "numero: " +
                "cvv: " + cvv + "\nm_exp" + m_exp + "\na_exp: " + a_exp
                + "\nemail: " + email);

                //create token for a Culqi Charge
                Token token = new Token("llave_publica_culqi");

                token.createToken(getActivity(), card, new TokenCallback() {

                    @Override
                    public void onSuccess(JSONObject token) throws JSONException {

                        String object = token.getString("object");
                        String token_id = token.getString("id");
                        JSONObject clientObject = token.getJSONObject("client");

                        Log.d("culqi", "id: " + token_id);

                        createCharge(token_id, email);

                    }

                    @Override
                    public void onError(Exception error) {
                        Log.d("Pago", "Culqi Token Error is: " + error);
                    }
                });
            }
        });


        return view;
    }

    private void createCharge(String token_id, String email) throws JSONException {

        String url = "https://api.culqi.com/v2/charges";

        JSONObject payObj = new JSONObject();
        payObj.put("amount", 1000);
        payObj.put("currency_code", "PEN");
        payObj.put("email", email);
        payObj.put("source_id", token_id);

        JsonObjectRequest chargeRequest = new JsonObjectRequest(Request.Method.POST,
                url, payObj, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.d("culqi", "success response is: " + response.toString());
                final API_Connection noti= new API_Connection(getContext(), TAG, "https://fcm.googleapis.com/");


                noti.retrofitLoad();
                if(noti.getRetrofit()!=null){
                    Log.i(TAG, "Notificaciones: Hay internet");
                    enviarNotificacion(noti.getRetrofit(), currentWorker);
                }else
                {
                    Log.e(TAG, "Principal: se fue el internet");
                }


                //startActivity(new Intent(getActivity(), PaymentConfirmed.class));

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("culqi", "error response is: " + error.toString());

            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + "sk_test_DHNtQHnQQwxtdMGq");
                return headers;
            }

        };

        chargeRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(chargeRequest);

    }

    private void enviarNotificacion(Retrofit retrofit, Worker worker) {
        Log.i(TAG, "Enviar Notificacion");
        String clientName=currentClient.getUser().getFirstName()+" "+currentClient.getUser().getLastName();
        //String clientName="Luis";

        String token;
        if(worker.getToken()==null){
            token="gg";
        }
        else{
            token=worker.getToken();
        }
        Log.i(TAG, token);
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

                    Log.e(TAG, " Notificacion-onResponse1: " + response.raw());
                    Log.e(TAG, " Notificacion-onResponse: " + response.errorBody().toString());
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
        DetailPost detallePots= new DetailPost(currentWorker.getId(), currentClient.getId(), currentService.getId(), infoWorker.getLatitude(), infoWorker.getLongitude());
        styleapp_API service = retrofit.create(styleapp_API.class);
        Call<DetailPostResponse> Call = service.creatDetalle(detallePots);
        Call.enqueue(new Callback<DetailPostResponse>() {
            @Override
            public void onResponse(Call<DetailPostResponse> call, Response<DetailPostResponse> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Se envio su solicitud", Toast.LENGTH_SHORT).show();

                    Intent intent= new Intent(getApplicationContext(), PaymentConfirmed.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
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
