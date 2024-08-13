package com.example.appbanmypham.model;

public class Admin {
    String email, phone, username, password;
    public Admin(String email, String phone, String username, String password) {
        this.email = email;
        this.phone = phone;
        this.username = username;
        this.password = password;
    }


    public Admin() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
