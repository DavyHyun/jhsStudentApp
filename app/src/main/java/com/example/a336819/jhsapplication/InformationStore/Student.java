package com.example.a336819.jhsapplication.InformationStore;

public class Student {
    String Name;
    String Grade;
    String uID;
    String phonenum;

    public Student(String name, String grade, String uid, String phonenum) {
        this.Name=name;
        this.Grade=grade;
        this.uID=uid;
        this.phonenum=phonenum;
    }

    public Student() {

    }

    public String getStudentID() {
        return this.uID;
    }
    public String getStudentName() {
        return this.Name;
    }
    public String getStudentGrade() {
        return this.Grade;
    }
    public String getStudentPhoneNum() { return this.phonenum;}

}
