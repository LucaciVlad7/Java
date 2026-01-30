package Repo;

import Domain.Masina;
import Domain.Status;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RepoMasina {
    private Connection connection;

    public RepoMasina(Connection connection) {
        this.connection = connection;
    }

    public List<Masina> getMasini() {
        List<Masina> masini = new ArrayList<>();
        String query = "SELECT * FROM Masina";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int idMasina = resultSet.getInt("idMasina");
                    String descriere = resultSet.getString("descriere");
                    int price = resultSet.getInt("price");
                    String statusStr = resultSet.getString("status");
                    Status status = Status.valueOf(statusStr.toUpperCase());

                    Masina masina = new Masina(idMasina, descriere, price, status);
                    masini.add(masina);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return masini;
    }

    public List<Masina> getMasiniForApproval() {
        List<Masina> masini = new ArrayList<>();
        String query = "SELECT * FROM Masina WHERE status='NEEDS_APPROVAL'";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int idMasina = resultSet.getInt("idMasina");
                    String descriere = resultSet.getString("descriere");
                    int price = resultSet.getInt("price");
                    String statusStr = resultSet.getString("status");
                    Status status = Status.valueOf(statusStr.toUpperCase());

                    Masina masina = new Masina(idMasina, descriere, price, status);
                    masini.add(masina);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return masini;
    }

    public void updateStatus(int idMasina, Status status) {
        String query = "UPDATE Masina SET status = ? WHERE idMasina = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, status.name());
            statement.setInt(2, idMasina);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
