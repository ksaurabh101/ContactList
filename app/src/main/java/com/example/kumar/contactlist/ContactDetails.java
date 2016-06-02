package com.example.kumar.contactlist;

/**
 * Created by kumar on 31-May-16.
 */
public class ContactDetails {
    private String name;
    private String number;
    private String otp;
    private String time;

    public ContactDetails() {
        name = "";
        number = "";
        otp = "";
        time = "";
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
