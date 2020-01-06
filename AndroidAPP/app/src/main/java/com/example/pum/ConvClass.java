package com.example.pum;

public class ConvClass {

    private String user, convTxt, dateTime;

    ConvClass(String user, String convtxt, String dateTime ){
        this.user = user;
        this.convTxt = convtxt;
        this.dateTime = dateTime;
    }
    String getTime(){
        return this.dateTime;
    }

    String getConvTxt(){
        return this.convTxt;
    }

    String getConvUser(){
        return this.user;
    }

}
