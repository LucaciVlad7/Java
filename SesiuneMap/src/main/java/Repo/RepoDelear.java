package Repo;

import Domain.Admin;
import Domain.Dealer;
import Domain.Masina;
import Domain.Status;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RepoDelear {
    private Connection connection;

    public RepoDelear(Connection connection) {
        this.connection = connection;
    }

    public List<Dealer> getDealers() {
        List<Dealer> dealers = new ArrayList<>();
        String query = "select * from DealerSes";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("idDealer");
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("pasword");
                    Dealer dealer = new Dealer(id, username, password);
                    dealers.add(dealer);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dealers;
    }
}
