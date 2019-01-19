package com.example.hp.thekleaners.pojoClass;

import com.google.firebase.firestore.Exclude;

public class ForCarService {
    private String documentId;
    private String carName;
    private String carNumber;
    private String carType;


    public ForCarService() {
        //public no-arg constructor needed
    }


    public ForCarService(String carName, String carNumber, String carType) {
        this.carName = carName;
        this.carNumber = carNumber;
        this.carType = carType;

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
}