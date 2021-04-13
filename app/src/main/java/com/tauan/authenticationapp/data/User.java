package com.tauan.authenticationapp.data;

public class User {
    private String name;
    private String email;
    private String uid;
    private String password;
    private String phone;

    public User() {

    }

    public User(String name, String email, String uid, String phone, String password) {
        this.name = name;
        this.email = email;
        this.uid = uid;
        this.phone = phone;
        this.password = password;
    }

    public User(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getUid() {
        return uid;
    }
}
