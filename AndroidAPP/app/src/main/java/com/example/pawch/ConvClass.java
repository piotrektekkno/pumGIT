package com.example.pawch;

public class ConvClass {
    String user, convtxt, dateTime;
    ConvClass(String user, String convtxt, String dateTime ){
        this.user = user;
        this.convtxt = convtxt;
        this.dateTime = dateTime;
    }
    String getTime(){
        return this.dateTime;
    }

    String getConvTxt(){
        return this.convtxt;
    }

    String getConvUser(){
        return this.user;
    }

}
