package com.styleappteam.styleapp.classes;

/**
 * Created by Luis on 06/06/2017.
 */

public class Service {
    private String serviceName;
    private int imgsrc;

    public Service(String serviceName, int imgsrc){
        this.serviceName=serviceName;
        this.imgsrc=imgsrc;
    }
    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(int imgsrc) {
        this.imgsrc = imgsrc;
    }
}
