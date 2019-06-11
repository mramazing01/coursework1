package com.jonas;

import java.sql.*;

public class Main {

    public static void main(String[] args) {
        Connection conn = null;
        try {
            // db parameters
            String url       = "jdbc:mysql://localhost:3306/mysqljdbc";
            String user      = "root";
            String password  = "legoland01";

            // create a connection to the database
            conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            //here is the query you will execute
            ResultSet rs = stmt.executeQuery("SELECT * FROM candidates");
            System.out.println(rs);
            while (rs.next()) {
                //rs contains the result of the query
                //with getters you can obtain column values ouykcuf
                int x = rs.getInt("id");
                String fna = rs.getString("first_name");
                String lna = rs.getString("last_name");
                String phone=rs.getString("email");
                if(fna.charAt(fna.length()-1)==' '){
                    fna=fna.substring(0,fna.length()-1);
                }
                System.out.println(x+" "+fna+" "+lna+" "+phone);
            }
            // more processing here
            // ...
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try{
                if(conn !=null)
                conn.close();
            }catch(SQLException ex){
                System.out.println(ex.getMessage());
            }
        }
        System.out.println("Here");
        /*
        try {
            System.out.println("Hello World");
            //connect to the database
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysqljdbc", "jonas", "abcd");
            Statement stmt = con.createStatement();
            //here is the query you will execute
            ResultSet rs = stmt.executeQuery("SELECT * FROM students");
            System.out.println(rs);

            while (rs.next()) {
                System.out.println(rs);
                //rs contains the result of the query
                //with getters you can obtain column values
                /*
                int x = rs.getInt("a");
                String s = rs.getString("b");
                float f = rs.getFloat("c");
            }
        } catch (Exception e) {
            System.out.println(e);
        }*/
    }
}