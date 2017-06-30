package com.styleappteam.styleapp.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.styleappteam.styleapp.R;
import com.styleappteam.styleapp.model.Services;
import com.styleappteam.styleapp.model.Type;

/**
 * Created by Luis on 14/06/2017.
 */

public class Services_Adapter extends ArrayAdapter<Services> {
private int r;
public Services_Adapter(Activity context, int resource){
        super(context, resource);
        r=resource;
        }
@Override
public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(r, parent, false);
            }
            Services currentType = getItem(position);

            TextView servicet = (TextView) listItemView.findViewById(R.id.serviceName);
            ImageView img = (ImageView) listItemView.findViewById(R.id.basicImg);


            servicet.setText(currentType.getName());
            switch(currentType.getId()){
                case 1:img.setImageResource(R.drawable.corte_hippie); break;
                case 2:img.setImageResource(R.drawable.corte_militar); break;
                case 3:img.setImageResource(R.drawable.corte_escolar); break;
                case 4:img.setImageResource(R.drawable.pedicure_dama); break;
                case 5:img.setImageResource(R.drawable.pedicure_caballero); break;
                case 6:img.setImageResource(R.drawable.manicure_dama); break;
                case 7:img.setImageResource(R.drawable.manicure_caballero); break;
                default:img.setImageResource(R.drawable.generictype); break;


            }
            //img.setImageResource(currentService.getImgsrc());

            return listItemView;
        }

}