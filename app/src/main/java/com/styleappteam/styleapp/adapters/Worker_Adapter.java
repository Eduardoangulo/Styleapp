package com.styleappteam.styleapp.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.styleappteam.styleapp.R;
import com.styleappteam.styleapp.model.Worker;

/**
 * Created by Luis on 09/06/2017.
 */

public class Worker_Adapter extends ArrayAdapter<Worker> {
    private int r;
    public Worker_Adapter(Activity context, int resource){
        super(context, resource);
        r=resource;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(r, parent, false);
        }
        Worker currentWorker = getItem(position);

        TextView workerName = (TextView) listItemView.findViewById(R.id.workerName);
        ImageView stars = (ImageView) listItemView.findViewById(R.id.stars);
        ImageView img = (ImageView) listItemView.findViewById(R.id.worker_image);
        TextView distanceText = (TextView)listItemView.findViewById(R.id.distanceText);
        TextView price = (TextView)listItemView.findViewById(R.id.cost);

        workerName.setText(currentWorker.getFull_name());
        distanceText.setText(currentWorker.getDistance().toString());
        price.setText("Costo: "+currentWorker.getPrice());

        //stars.setImageResource(currentWorker.getValoration());
        //img.setImageResource(currentWorker.getImg());

        return listItemView;

    }

}
