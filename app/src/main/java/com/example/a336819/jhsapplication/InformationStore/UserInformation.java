package com.example.a336819.jhsapplication.InformationStore;

public class UserInformation {

    private String name;
    private String email;
    private String phone_num;
    private String ID;

    public UserInformation(){

    }

    public String getstudentID() {
        return ID;
    }

    public void setstudentID(String ID) {
        this.ID = ID;
    }

    public String getstudentGrade() {
        return email;
    }

    public void setstudentGrade(String email) {
        this.email = email;
    }

    public String getstudentName() {
        return name;
    }

    public void setstudentName(String name) {
        this.name = name;
    }

    public String getstudentPhoneNum() {
        return phone_num;
    }

    public void setstudentPhoneNum(String phone_num) {
        this.phone_num = phone_num;
    }
}
