package org.example;

import java.sql.Connection;
import java.sql.*;

public class JDBC {
    public void test_connection() {
        try{
            String url = "jdbc:mysql://db4free.net:3306/librarydbtest1";
            String login = "janczor1";
            String password = "2.kfkDFZFdJwK2k";
            System.out.println("connecting...");
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, login, password);

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
}
