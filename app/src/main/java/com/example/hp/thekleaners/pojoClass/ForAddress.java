package com.example.hp.thekleaners.pojoClass;

import com.google.firebase.firestore.Exclude;

import java.util.Map;

public class ForAddress {
    private String documentId;
    private String address;
    private String landmark;
    private String pincode;
    private String selectState;
    private String selectCity;
    private String house;

    public ForAddress() {
        //public no-arg constructor needed
    }

    public ForAddress(String address, String landmark, String pincode, String selectState
            , String selectCity,String house){

        this.address = address;
        this.landmark = landmark;
        this.pincode = pincode;
        this.selectState = selectState;
        this.selectCity = selectCity;
        this.house= house;
    }

    @Exclude
    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getAddress() {
        return address;
    }

    public String getLandmark() {
        return landmark;
    }

    public String getPincode() {
        return pincode;
    }

    public String getSelectState() {
        return selectState;
    }

    public String getSelectCity() { return selectCity; }

    public String getHouse() { return house; }
}