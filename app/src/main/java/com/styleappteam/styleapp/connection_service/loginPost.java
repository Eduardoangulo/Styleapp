package com.styleappteam.styleapp.connection_service;

/**
 * Created by Luis on 29/06/2017.
 */

public class loginPost {
    private String email;
    private String password;
    private String token;

    public loginPost(String email, String password, String token) {
        this.email = email;
        this.password = password;
        this.token=token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
