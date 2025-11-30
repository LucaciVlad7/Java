package org.example;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDBRepository {
    private String url;
    private String username;
    private String password;

    UserDBRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public List<User> getAll(){
        List<User> users = new ArrayList<>();
        try(Connection connection =DriverManager.getConnection(url,username,password)){
            System.out.println("Connected to the database");

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users");
            ResultSet res=preparedStatement.executeQuery();
            while(res.next()){
                long id=res.getLong(1);
                String name1=res.getString(2);
                String name2=res.getString(3);
                User u= new User(id,name1,name2);
                users.add(u);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }
}
