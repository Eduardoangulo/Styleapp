package com.styleappteam.styleapp.fragments.fragments_promociones;

import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.styleappteam.styleapp.R;

/**
 * Created by Luis on 25/06/2017.
 */

public class addCouponDialog extends DialogFragment {

    public void setmListener(addCouponDialogListener mListener) {
        this.mListener = mListener;
    }

    public interface addCouponDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog, String result);
        public void onDialogNegativeClick(DialogFragment dialog);
    }
    private addCouponDialogListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view= inflater.inflate(R.layout.coupon_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(view)
                .setMessage("Ingresar Codigo Promocional")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                        EditText couponCode=(EditText) view.findViewById(R.id.couponCode);
                        Log.i("DIALOGLOGS", "codigo cupon: "+ couponCode.getText().toString());
                        mListener.onDialogPositiveClick(addCouponDialog.this, couponCode.getText().toString());
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDialogNegativeClick(addCouponDialog.this);
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}