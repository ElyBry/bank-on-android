package com.example.bank2.database;

public class Config {
    // Для Android эмулятора используйте 10.0.2.2 вместо localhost
    // Для реального устройства используйте IP адрес вашего компьютера в локальной сети
    protected String dbHost = "10.0.2.2"; // IP для Android эмулятора (замените на IP вашего ПК для реального устройства)
    protected String dbPort = "3306";
    protected String dbUser = "testsforusers";
    protected String dbPass = "testsforusers";
    protected String dbName = "ermakoff";
}
