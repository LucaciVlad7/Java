package Domain;

import java.sql.Timestamp;

public class Order {
    private Integer orderId;
    private Integer id_table;
    private Integer id_item;
    private Timestamp orderDate;
    private Status status;

    public Order(Integer orderId, Integer id_table, Integer id_item, Timestamp orderDate, Status status) {
        this.orderId = orderId;
        this.id_table = id_table;
        this.id_item = id_item;
        this.orderDate = orderDate;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", id_table=" + id_table +
                ", id_item=" + id_item +
                ", orderDate=" + orderDate +
                ", status=" + status +
                '}';
    }

    public Integer getOrderId() {
        return orderId;
    }

    public Integer getId_table() {
        return id_table;
    }

    public Integer getId_item() {
        return id_item;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Status getStatus() {
        return status;
    }
}
