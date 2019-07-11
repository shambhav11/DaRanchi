package com.speedofy.app.daranchi;

public class RecyclerActivityModel {

    public String primaryData;
    public String SecondaryData;
    public String type;
    public String verificationData;

    public RecyclerActivityModel(String pd,String sd,String verificationData,String type)
    {
        this.primaryData=pd;
        this.SecondaryData=sd;
        this.verificationData=verificationData;
    this.type=type;
    }


    public String getPrimaryData() {
        return primaryData;
    }

    public String getSecondaryData() {
        return SecondaryData;
    }

    public String getVerificationData() {
        return verificationData;
    }

    public String getType() {
        return type;
    }
}
