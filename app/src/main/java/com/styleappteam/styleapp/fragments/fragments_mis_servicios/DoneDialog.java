package com.styleappteam.styleapp.fragments.fragments_mis_servicios;

/**
 * Created by Luis on 13/07/2017.
 */
import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.styleappteam.styleapp.R;

public class DoneDialog extends DialogFragment {

    public void setDoneDialogListener(DoneDialogListener mListener) {
        this.listener = mListener;
    }

    public interface DoneDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        //public void onDialogNegativeClick(DialogFragment dialog);
    }
    private DoneDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view= inflater.inflate(R.layout.done_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(view)
                .setMessage("Marcar como realizado?")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDialogPositiveClick(DoneDialog.this);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //listener.onDialogNegativeClick(DoneDialog.this);
                    }
                });

        return builder.create();
    }
}