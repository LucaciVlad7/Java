package Controller;

import Domain.Order;
import Observer.Observer;
import Service.Service;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class ControllerStaff implements Observer {
    private Service service;

    @FXML
    private Label label;

    @FXML
    private ListView<Order> listViewPlacedOrders;


    @Override
    public void update() {
        listViewPlacedOrders.setItems(FXCollections.observableArrayList(service.getOrdersService()));
    }

    public void setService(Service service) {
        this.service = service;
        this.service.addObserver(this);
        update();
    }
}
