package com.melita.cafeshop;

import java.sql.Connection;
import java.sql.DriverManager;

//singleton design pattern to use one instance for whole project
public class database {

    private static database instance;
    private Connection connect;

    private database() {
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            connect = DriverManager.getConnection("jdbc:mysql://localhost/cafeshop", "root", ""); // LINK YOUR DATABASE
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
            return connect;
    }
    public static database getInstance() {
        if (instance == null) {
            instance = new database();
        }
        return instance;
    }

}
