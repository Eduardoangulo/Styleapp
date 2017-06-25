package com.styleappteam.styleapp.fragments_main.fragments_principal;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.styleappteam.styleapp.Culqi.Card;
import com.styleappteam.styleapp.Culqi.Token;
import com.styleappteam.styleapp.Culqi.TokenCallback;
import com.styleappteam.styleapp.Culqi.Validation;
import com.styleappteam.styleapp.R;

import org.json.JSONObject;

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

        txtcardnumber.setTransformationMethod(new NumericKeyBoardTransformationMethod());
        txtcvv.setTransformationMethod(new NumericKeyBoardTransformationMethod());
        txtmonth.setTransformationMethod(new NumericKeyBoardTransformationMethod());
        txtyear.setTransformationMethod(new NumericKeyBoardTransformationMethod());
        txtemail.setTransformationMethod(new NumericKeyBoardTransformationMethod());

        txtcvv.setEnabled(true);

        txtcardnumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    txtcvv.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = txtcardnumber.getText().toString();
                if (s.length() == 0) {
                    txtcardnumber.setBackgroundResource(R.drawable.border_error);
                }

                if (validation.luhn(text)) {
                    txtcardnumber.setBackgroundResource(R.drawable.border_sucess);
                } else {
                    txtcardnumber.setBackgroundResource(R.drawable.border_error);
                }

                int cvv = validation.bin(text, kind_card);
                if (cvv > 0) {
                    txtcvv.setFilters(new InputFilter[]{new InputFilter.LengthFilter(cvv)});
                    txtcvv.setEnabled(true);
                } else {
                    txtcvv.setEnabled(true);
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
                if (validation.year(text)) {
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
                if (validation.month(text)) {
                    txtmonth.setBackgroundResource(R.drawable.border_error);
                } else {
                    txtmonth.setBackgroundResource(R.drawable.border_sucess);
                }
            }
        });

        btnPay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                progress.show();

                Card card = new Card(txtcardnumber.getText().toString(), txtcvv.getText().toString(), Integer.parseInt(txtmonth.getText().toString()), Integer.parseInt(txtyear.getText().toString()), txtemail.getText().toString());

                Token token = new Token("pk_test_QqGjpvWBQMeLI3Uz");

                token.createToken(getContext(), card, new TokenCallback() {
                    @Override
                    public void onSuccess(JSONObject token) {
                        try {
                            result.setText(token.get("id").toString());
                        } catch (Exception ex) {
                            progress.hide();
                        }
                        progress.hide();
                    }

                    @Override
                    public void onError(Exception error) {
                        progress.hide();
                    }
                });

            }
        });

        return view;
    }
    private class NumericKeyBoardTransformationMethod extends PasswordTransformationMethod {
        @Override
        public CharSequence getTransformation(CharSequence source, View view) {
            return source;
        }
    }
}
