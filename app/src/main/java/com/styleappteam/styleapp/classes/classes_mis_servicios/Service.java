package com.styleappteam.styleapp.classes.classes_mis_servicios;

/**
 * Created by Luis on 06/06/2017.
 */

public class Service {
    private String serviceName;
    private int imgsrc;
    private String state;

    public Service(String serviceName, String state, int imgsrc){
        this.serviceName=serviceName;
        this.imgsrc=imgsrc;
        this.state=state;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
