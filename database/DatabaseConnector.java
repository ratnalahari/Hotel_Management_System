package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {

    static {
        try {Class.forName("com.mysql.cj.jdbc.Driver");}
        catch (ClassNotFoundException e) 
        {System.out.println("Exception in loading driver: " + e);}
        System.out.println("Driver Loaded Successfully");
    }

    static final String DB_URL = "jdbc:mysql://localhost:3306/hotel";
    static final String USER = "root";
    static final String PWD = "alskzm";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PWD);
    }
}