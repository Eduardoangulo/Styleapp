package com.styleappteam.styleapp.classes;

/**
 * Created by Luis on 09/06/2017.
 */

public class worker {
    private String name;
    private int valoration;
    private int img;
    public worker(String name, int valoration, int img){
        this.name=name;
        this.valoration=valoration;
        this.img=img;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValoration() {
        return valoration;
    }

    public void setValoration(int valoration) {
        this.valoration = valoration;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
