package com.example.bank2.models;

public class Card {
    private static int id_card;
    private static String number;
    private static String holder_name;
    private static String expiry_date;
    private static String cvv;
    private static String balance;
    private static boolean is_active;

    public static int getId_card() {
        return id_card;
    }

    public static void setId_card(int id_card) {
        Card.id_card = id_card;
    }

    public static String getNumber() {
        return number;
    }

    public static void setNumber(String number) {
        Card.number = number;
    }

    public static String getHolder_name() {
        return holder_name;
    }

    public static void setHolder_name(String holder_name) {
        Card.holder_name = holder_name;
    }

    public static String getExpiry_date() {
        return expiry_date;
    }

    public static void setExpiry_date(String expiry_date) {
        Card.expiry_date = expiry_date;
    }

    public static String getCvv() {
        return cvv;
    }

    public static void setCvv(String cvv) {
        Card.cvv = cvv;
    }

    public static String getBalance() {
        return balance;
    }

    public static void setBalance(String balance) {
        Card.balance = balance;
    }

    public static boolean isIs_active() {
        return is_active;
    }

    public static void setIs_active(boolean is_active) {
        Card.is_active = is_active;
    }
}
