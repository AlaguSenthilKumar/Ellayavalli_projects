package com.alwar.elayavalli.Models;

import java.io.Serializable;

import io.realm.RealmObject;

public class Profile extends RealmObject implements Serializable {


    private String name, gender, dob, address, IRNumber;

    public Profile() {
    }

    public Profile(String name, String gender, String dob, String address, String irNumber) {
        this.name = name;
        this.gender = gender;
        this.dob = dob;
        this.address = address;
        IRNumber = irNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIRNumber() {
        return IRNumber;
    }

    public void setIRNumber(String IRNumber) {
        this.IRNumber = IRNumber;
    }
}
