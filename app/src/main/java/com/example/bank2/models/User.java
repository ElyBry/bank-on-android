package com.example.bank2.models;

public class User {
    private static int id;
    private static String login;
    private static String fio;
    private static String passport;
    private static String dateOfBirth;
    private static String sex;
    private static String numberPhone;
    private static int admin;

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        User.id = id;
    }

    public static String getLogin() {
        return login;
    }

    public static void setLogin(String login) {
        User.login = login;
    }

    public static String getFio() {
        return fio;
    }

    public static void setFio(String fio) {
        User.fio = fio;
    }

    public static String getPassport() {
        return passport;
    }

    public static void setPassport(String passport) {
        User.passport = passport;
    }

    public static String getDateOfBirth() {
        return dateOfBirth;
    }

    public static void setDateOfBirth(String dateOfBirth) {
        User.dateOfBirth = dateOfBirth;
    }

    public static String getSex() {
        return sex;
    }

    public static void setSex(String sex) {
        User.sex = sex;
    }

    public static String getNumberPhone() {
        return numberPhone;
    }

    public static void setNumberPhone(String numberPhone) {
        User.numberPhone = numberPhone;
    }

    public static int getAdmin() {
        return admin;
    }

    public static void setAdmin(int admin) {
        User.admin = admin;
    }
}
