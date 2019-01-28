package com.example.hp.thekleaners.pojoClass;

import com.google.firebase.firestore.Exclude;

public class ForCarService {
    private String documentId;
    private String carName;
    private String carNumber;
    private String carType;
    private String carDate;
    private int carAmount;


    public ForCarService() {
        //public no-arg constructor needed
    }


    public ForCarService(String carName, String carNumber, String carType,String carDate,int carAmount) {
        this.carName = carName;
        this.carNumber = carNumber;
        this.carType = carType;
        this.carDate= carDate;
        this.carAmount= carAmount;

    }


    @Exclude
    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getCarName() {
        return carName;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public String getCarType() {
        return carType;
    }

    public int getCarAmount() { return carAmount; }

    public String getCarDate() { return carDate; }
}