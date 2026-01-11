package com.example.bank2.models;

public class Vklad {
    private static double sum;
    private static int percent;
    private static double getmon;
    private static String number_vklad;
    private static String create_date;
    private static String end_date;
    private static String user_login;

    public static double getSum() {
        return sum;
    }

    public static void setSum(double sum) {
        Vklad.sum = sum;
    }

    public static int getPercent() {
        return percent;
    }

    public static void setPercent(int percent) {
        Vklad.percent = percent;
    }

    public static double getGetmon() {
        return getmon;
    }

    public static void setGetmon(double getmon) {
        Vklad.getmon = getmon;
    }

    public static String getNumber_vklad() {
        return number_vklad;
    }

    public static void setNumber_vklad(String number_vklad) {
        Vklad.number_vklad = number_vklad;
    }

    public static String getCreate_date() {
        return create_date;
    }

    public static void setCreate_date(String create_date) {
        Vklad.create_date = create_date;
    }

    public static String getEnd_date() {
        return end_date;
    }

    public static void setEnd_date(String end_date) {
        Vklad.end_date = end_date;
    }

    public static String getUser_login() {
        return user_login;
    }

    public static void setUser_login(String user_login) {
        Vklad.user_login = user_login;
    }
}
