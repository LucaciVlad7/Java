package Repo;

import Domain.Order;
import Domain.Status;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RepoOrder {
    private Connection connection;

    public RepoOrder(Connection connection) {
        this.connection = connection;
    }

    public Order save(Order order) {
        String sql = "INSERT INTO OrderUlt (id_table, id_item, orderDate, status) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, order.getId_table());
            statement.setInt(2, order.getId_item());
            statement.setTimestamp(3, order.getOrderDate());
            statement.setString(4, order.getStatus().name());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {

                System.out.println("Comanda a fost salvată cu succes!");
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1);
                        order.setOrderId(generatedId);
                    }
                    return order;
                }
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return null;
    }

    public List<Order> getOrders() {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM OrderUlt ORDER BY orderDate ASC";
        try(PreparedStatement statement= connection.prepareStatement(query)) {
            try(ResultSet resultSet=statement.executeQuery()){
                while(resultSet.next()) {
                    int id_order = resultSet.getInt("id_order");
                    int id_table = resultSet.getInt("id_table");
                    int id_item = resultSet.getInt("id_item");
                    java.sql.Timestamp orderDate = resultSet.getTimestamp("orderDate");
                    String statusStr = resultSet.getString("status");
                    Status status = Status.valueOf(statusStr);

                    Order order = new Order(id_order, id_table, id_item, orderDate, status);
                    orders.add(order);
                }
                return orders;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }
}
