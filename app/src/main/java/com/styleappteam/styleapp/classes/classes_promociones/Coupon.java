package com.styleappteam.styleapp.classes.classes_promociones;

/**
 * Created by Luis on 06/06/2017.
 */

public class Coupon {
    private String couponName;
    private int imgsrc;

    public Coupon(String couponName, int imgsrc){
        this.couponName=couponName;
        this.imgsrc=imgsrc;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public int getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(int imgsrc) {
        this.imgsrc = imgsrc;
    }
}
