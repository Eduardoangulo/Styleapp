package com.styleappteam.styleapp.classes;

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


 /* Created by Luis on 05/05/2017.
 */

public class coupons_adapter extends ArrayAdapter<Coupon> {
    private int r;
    public coupons_adapter(Activity context, int resource){
        super(context, resource);
        r=resource;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(r, parent, false);
        }
        Coupon currentCoupon = getItem(position);

        TextView couponN = (TextView) listItemView.findViewById(R.id.basicText);
        ImageView img = (ImageView) listItemView.findViewById(R.id.basicImg);

        couponN.setText(currentCoupon.getCouponName());
        img.setImageResource(currentCoupon.getImgsrc());

        return listItemView;

    }

}
