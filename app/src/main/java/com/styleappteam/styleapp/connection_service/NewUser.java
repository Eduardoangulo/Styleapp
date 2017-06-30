package com.styleappteam.styleapp.connection_service;

/**
 * Created by Luis on 29/06/2017.
 */

public class NewUser {
    	private final String _encrypted="StyleAppHome2017Api";
        private final String photo="https://placehold.it/100";
        private Integer telephone;
        private String first_name;
        private String last_name;
        private String email;
        private String password;

        public NewUser(Integer telephone, String first_name, String last_name, String email, String password) {
            this.setTelephone(telephone);
            this.setFirst_name(first_name);
            this.setLast_name(last_name);
            this.setEmail(email);
            this.setPassword(password);
        }

    public String get_encrypted() {
        return _encrypted;
    }

    public String getPhoto() {
        return photo;
    }


    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
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

    public Integer getTelephone() {
        return telephone;
    }

    public void setTelephone(Integer telephone) {
        this.telephone = telephone;
    }
}
