package com.styleappteam.styleapp.classes;

/**
 * Created by Luis on 14/06/2017.
 */

public class instancedService {
    private String serviceName;
    private int imgsrc;
    private String state;

    public instancedService(String serviceName, String state, int imgsrc){
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
