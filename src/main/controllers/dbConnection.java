package main.com.pluralsight.controllers;

import java.sql.Connection;
import java.sql.DriverManager;

public class dbConnection {
    static Connection con;
    public static Connection getConnection(){
        try{
            Class.forName("com.mysql.jdbc.Driver");                 //reads the driver library
            String db_url ="jdbc:mysql://localhost:3306/jorgen",
                    db_userName = "root",
                    db_password = "";
            con = DriverManager.getConnection(db_url,db_userName,db_password); //makes the connection
        } catch(ClassNotFoundException ex) {
            System.out.println("Error: unable to load driver class!");
            System.exit(1);
        } catch(Exception sqlException) {
            sqlException.printStackTrace();
        }
        return con;
    }
}
