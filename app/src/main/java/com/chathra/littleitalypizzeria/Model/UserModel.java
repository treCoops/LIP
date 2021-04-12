package com.chathra.littleitalypizzeria.Model;

public class UserModel {
    private String fullName;
    private String uID;
    private String email;

    public UserModel(){

    }

    public UserModel(String fullName, String uID, String email){
        this.fullName = fullName;
        this.uID = uID;
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public String getuID() {
        return uID;
    }

    public String getEmail() {
        return email;
    }
}
