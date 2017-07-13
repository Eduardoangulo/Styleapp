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
import android.widget.RatingBar;

import com.styleappteam.styleapp.R;

public class RatingDialog extends DialogFragment {

    public void setRatingDialogListener(RatingDialogListener mListener) {
        this.listener = mListener;
    }

    public interface RatingDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog, float rating);
        //public void onDialogNegativeClick(DialogFragment dialog);
    }

    private RatingDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.rating_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(view)
                .setMessage("Valora el servicio")
                .setPositiveButton(getResources().getString(R.string.accept), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        RatingBar rating= (RatingBar) view.findViewById(R.id.ratingBar);
                        listener.onDialogPositiveClick(RatingDialog.this, rating.getRating());
                    }
                })
                .setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //listener.onDialogNegativeClick(RatingDialog.this);
                    }
                });

        return builder.create();
    }
}
