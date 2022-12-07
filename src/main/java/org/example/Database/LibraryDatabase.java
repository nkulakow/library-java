package org.example.Database;

import java.sql.Connection;
import java.sql.*;
import java.util.ArrayList;

public class LibraryDatabase {
    //logging stuff

    private static final String URL = "jdbc:mysql://db4free.net:3306/librarydbtest1";
    private static final String LOGIN = "janczor1";
    private static final String PASSWORD = "2.kfkDFZFdJwK2k";
    private static Connection CONNECTION;
    public static void test_connection() {
        try{
            System.out.println("connecting...");
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery("select * from users");
            while (resultset.next()){
                System.out.println(resultset.getString("user_id"));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void connect() {
        try {
            CONNECTION = DriverManager.getConnection(URL, LOGIN, PASSWORD);
        } catch (java.sql.SQLException bad_connection) {
            System.out.println("Could not connect to database.");
        }
    }
    private static ArrayList<UserInDB> searchUsers(final String query_str) {
        ArrayList<UserInDB> users = new ArrayList<>();
        try {
            LibraryDatabase.connect();
            Statement query = CONNECTION.createStatement();
            ResultSet result = query.executeQuery(query_str);
            while (result.next()) {
                int id = result.getInt(DatabaseConstants.USER_ID);
                String name = result.getString(DatabaseConstants.USER_NAME);
                String surname = result.getString(DatabaseConstants.USER_SURNAME);
                users.add(new UserInDB(id, name, surname));
            }
        } catch (java.sql.SQLException e) {
            System.out.println("Could not execute query.");
        }
        return users;
    }

    public static ArrayList<String> getUsers(final String query_str) {
        ArrayList<String> result = new ArrayList<>();
        var users = searchUsers(query_str);
        for(var user : users) {
            String respresentation = user.getId() + " " + user.getName() + " " + user.getSurname();
            result.add(respresentation);
        }
        return result;
    }
}

class DatabaseConstants {
    public static final int TABLE_USERS = 0;
    public static final int TABLE_BOOKS = 1;
    public static final int TABLE_LOANS = 2;
    public static final String USER_ID = "user_id";
    public static final String USER_NAME = "first_name";
    public static final String USER_SURNAME = "last_name";
}