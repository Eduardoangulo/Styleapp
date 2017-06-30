package com.styleappteam.styleapp.adapters;

/**
 * Created by Luis on 06/06/2017.
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.styleappteam.styleapp.R;
import com.styleappteam.styleapp.connection_service.DetailClient;


 /* Created by Luis on 05/05/2017.
 */

public class misservicios_Adapter extends ArrayAdapter<DetailClient> {
    private int r;
    public misservicios_Adapter(Activity context, int resource){
        super(context, resource);
        r=resource;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(r, parent, false);
        }
        DetailClient currentService = getItem(position);

        TextView servicet = (TextView) listItemView.findViewById(R.id.serviceName);
        TextView service_status = (TextView)listItemView.findViewById(R.id.status);
        ImageView img = (ImageView) listItemView.findViewById(R.id.basicImg);


        servicet.setText(currentService.getService().getName());

      switch(currentService.getService().getId()){
            case 1:img.setImageResource(R.drawable.corte_hippie); break;
            case 2:img.setImageResource(R.drawable.corte_militar); break;
            case 3:img.setImageResource(R.drawable.corte_escolar); break;
            case 4:img.setImageResource(R.drawable.pedicure_dama); break;
            case 5:img.setImageResource(R.drawable.pedicure_caballero); break;
            case 6:img.setImageResource(R.drawable.manicure_dama); break;
            case 7:img.setImageResource(R.drawable.manicure_caballero); break;
            default:img.setImageResource(R.drawable.generictype); break;
       }
        switch (currentService.getStatus()){
            case 0: service_status.setText("Cancelado"); break;
            case 1: service_status.setText("Realizado"); break;
            case 2: service_status.setText("En camino"); break;
            default: service_status.setText(" "); break;
        }
        return listItemView;

    }

}