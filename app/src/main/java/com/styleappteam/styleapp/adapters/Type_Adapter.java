package com.styleappteam.styleapp.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.styleappteam.styleapp.R;
import com.styleappteam.styleapp.model.Type_Service;

/**
 * Created by Luis on 14/06/2017.
 */

public class Type_Adapter extends ArrayAdapter<Type_Service> {
private int r;
public Type_Adapter(Activity context, int resource){
        super(context, resource);
        r=resource;
        }
@Override
public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(r, parent, false);
            }
            Type_Service currentService = getItem(position);

            TextView servicet = (TextView) listItemView.findViewById(R.id.serviceName);
            ImageView img = (ImageView) listItemView.findViewById(R.id.basicImg);


            servicet.setText(currentService.getName());
            //img.setImageResource(currentService.getImgsrc());

            return listItemView;
        }

}