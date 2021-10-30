package com.example.usermanagement.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtils {

    private static String url = "jdbc:postgresql://localhost:5432/crud";
    private static String user = "postgres";
    private static String pw = "azoo";

//    public static void main(String[] args) {
//        Connection result = getConnection();
//        System.out.println(result);
//    }

    public static Connection getConnection(){
        Connection connection = null;
        try{
            connection = DriverManager.getConnection(url, user, pw);
            System.out.println("Conncted");
        } catch(SQLException e){
            e.printStackTrace();
        }
        return connection;
    }

    public static void printSQLException(SQLException ex){
        for(Throwable e: ex){
            if(e instanceof SQLException){
                e.printStackTrace(System.err);
                System.err.println("SQLState : " + ((SQLException)e).getSQLState());
                System.err.println("Error Code : " + ((SQLException)e).getErrorCode());
                System.err.println("Message : " + e.getMessage());
                Throwable t = ex.getCause();
                while(t != null){
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }


            }
        }
    }

}
