package Repo;

import Domain.MenuItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RepoMenuItem {
    private Connection connection;

    public RepoMenuItem(Connection connection) {
        this.connection = connection;
    }

    public List<MenuItem> getMenuItems() {
        List<MenuItem> items = new ArrayList<>();
        String query = "SELECT * FROM MenuItemUlt ORDER BY category ASC";
        try(PreparedStatement statement=connection.prepareStatement(query)){
            try(ResultSet resultSet=statement.executeQuery()){
                while(resultSet.next()){
                    int id_item = resultSet.getInt("id_item");
                    String category = resultSet.getString("category");
                    String item = resultSet.getString("item");
                    float price = resultSet.getFloat("price");
                    String currency = resultSet.getString("currency");

                    MenuItem menuItem = new MenuItem(id_item, category, item, price,currency);
                    items.add(menuItem);
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }
}
