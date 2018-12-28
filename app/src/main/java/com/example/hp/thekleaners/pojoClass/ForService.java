package com.example.hp.thekleaners.pojoClass;

import com.google.firebase.firestore.Exclude;

import java.util.Map;

public class ForService {
    private String documentId;
    private String serviceTaken;
    private String amount;
     String date;

    public ForService() {
        //public no-arg constructor needed
    }


    public ForService( String serviceTaken, String amount, String date) {
        this.serviceTaken = serviceTaken;
        this.amount = amount;
        this.date = date;
    }


    @Exclude
    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getServiceTaken() {
        return serviceTaken;
    }

    public void setServiceTaken(String serviceTaken) {
        this.serviceTaken = serviceTaken;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String data) {
        this.date = data;
    }



}