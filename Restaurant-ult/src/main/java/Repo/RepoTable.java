package Repo;

import Domain.Table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RepoTable {
    private Connection conn;

    public RepoTable(Connection conn) {
        this.conn = conn;
    }

    public List<Table> getTables() {
        List<Table> tables = new ArrayList<>();
        String query = "SELECT * FROM TableUlt";
        try(PreparedStatement statement= conn.prepareStatement(query)) {
            try(ResultSet resultSet = statement.executeQuery()) {
                while(resultSet.next()) {
                    int id_table = resultSet.getInt("id_table");

                    Table table = new Table(id_table);
                    tables.add(table);
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return tables;
    }
}
