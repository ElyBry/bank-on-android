package com.example.bank2.models;

public class CardHistory {
    private String summa;
    private String date;
    private String operation;
    private String numbercardotpr;
    private String numbercardpol;
    private boolean success;
    private String fio_storyotpr;
    private String fio_storypol;
    private String numberpassportopl;
    private String numberpassportotpr;

    public String getSumma() {
        return summa;
    }

    public void setSumma(String summa) {
        this.summa = summa;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getNumbercardotpr() {
        return numbercardotpr;
    }

    public void setNumbercardotpr(String numbercardotpr) {
        this.numbercardotpr = numbercardotpr;
    }

    public String getNumbercardpol() {
        return numbercardpol;
    }

    public void setNumbercardpol(String numbercardpol) {
        this.numbercardpol = numbercardpol;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getFioOtpr() {
        return fio_storyotpr;
    }

    public void setFioOtpr(String fio_storyotpr) {
        this.fio_storyotpr = fio_storyotpr;
    }

    public String getFioPol() {
        return fio_storypol;
    }

    public void setFioPol(String fio_storypol) {
        this.fio_storypol = fio_storypol;
    }

    public String getNumPassOtpr() {
        return numberpassportopl;
    }

    public void setNumPassOtpr(String numberpassportopl) {
        this.numberpassportopl = numberpassportopl;
    }

    public String getNumPassPol() {
        return numberpassportotpr;
    }

    public void setNumPassPol(String numberpassportotpr) {
        this.numberpassportotpr = numberpassportotpr;
    }
}
