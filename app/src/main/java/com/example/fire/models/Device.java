package com.example.fire.models;

public class Device {

    private String username;
    private String token;
    private String local;
    private int id;

    public Device(String username, String token, String local, int id) {
        this.username = username;
        this.token = token;
        this.local = local;
        this.id = id;
    }



    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getUsername() {
        return username;
    }

    public String getToken() {
        return token;
    }

    public int getId() {
        return id;
    }
}
