package com.styleappteam.styleapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.styleappteam.styleapp.R;

public class PaymentConfirmed extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_confirmed);
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.view_orders_btn:
                startActivity(new Intent(PaymentConfirmed.this, MainActivity.class));
                break;
        }
    }
}
