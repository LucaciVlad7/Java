package Repo;

import Domain.Admin;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepoAdmin {
    private Connection connection;

    public RepoAdmin(Connection connection) {
        this.connection = connection;
    }

    public List<Admin> getAdmins() {
        List<Admin> admins = new ArrayList<>();
        String query = "select * from AdminSes";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("idAdmin");
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("pasword");
                    Admin admin = new Admin(id, username, password);
                    admins.add(admin);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admins;
    }
}
