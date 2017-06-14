package com.styleappteam.styleapp.classes;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.styleappteam.styleapp.R;

/**
 * Created by Luis on 14/06/2017.
 */

public class typeAdapter extends ArrayAdapter<type_service> {
private int r;
public typeAdapter(Activity context, int resource){
        super(context, resource);
        r=resource;
        }
@Override
public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(r, parent, false);
            }
            type_service currentService = getItem(position);

            TextView servicet = (TextView) listItemView.findViewById(R.id.serviceName);
            ImageView img = (ImageView) listItemView.findViewById(R.id.basicImg);


            servicet.setText(currentService.getName());
            //img.setImageResource(currentService.getImgsrc());

            return listItemView;
        }

}