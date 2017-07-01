package com.styleappteam.styleapp.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
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
        ImageView dist_icon = (ImageView)listItemView.findViewById(R.id.colordist);

        workerName.setText(currentWorker.getFirst_name()+" "+currentWorker.getLast_name());
        distanceText.setText(identificarKM(currentWorker.getDistance()));
        price.setText("Costo: S/."+currentWorker.getPrice());
        dist_icon.setImageResource(identificarIconoDistancia(currentWorker.getDistance()));
        Glide.with(getContext())
                .load(currentWorker.getPhoto())
                .fitCenter()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(img);

        if(currentWorker.getValue_calc()==null){
            currentWorker.setValue_calc(0);
        }
        switch(currentWorker.getValue_calc()){
            case 1:stars.setImageResource(R.drawable.stars_1); break;
            case 2:stars.setImageResource(R.drawable.stars_2); break;
            case 3:stars.setImageResource(R.drawable.stars_3); break;
            case 4:stars.setImageResource(R.drawable.stars_4); break;
            case 5:stars.setImageResource(R.drawable.stars_5); break;
            default: break;
        }
        //stars.setImageResource(currentWorker.getValoration());

        return listItemView;

    }
    private int identificarIconoDistancia(Double distancia ){
        int id_Icon;
        if(distancia<200){
            id_Icon = R.drawable.ic_verde;
        }else{
            if(distancia<500){
                id_Icon = R.drawable.ic_amarillo;
            }else{
                id_Icon = R.drawable.ic_rojo;
            }
        }
        return id_Icon;
    }

    private String identificarKM(Double distancia){
        String km_text="";
        if(distancia<=600){
            km_text=distancia+" m";
        }
        else{
            distancia=distancia/1000;
            km_text=distancia+" km";
        }
        return km_text;
    }


}
