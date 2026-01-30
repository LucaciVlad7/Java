package Controller;

import Domain.MenuItem;
import Domain.Order;
import Domain.Status;
import Domain.Table;
import Service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ControllerTable {
    private Service service;

    @FXML
    private Label tabelLabel;

    @FXML
    private TableView<MenuItem> tableViewItems;

    @FXML
    private TableColumn<MenuItem,String> item;

    @FXML
    private TableColumn<MenuItem,String> category;

    @FXML
    private TableColumn<MenuItem,Float> price;

    @FXML
    private TableColumn<MenuItem,String> currency;

    @FXML
    private Button orderButton;

    private Table currentTable;

    public void setService(Service service,Table table){
        this.service=service;
        this.currentTable=table;
        initData();
    }

    public void initData(){
        if(currentTable!=null){
            tabelLabel.setText(currentTable.toString());

            item.setCellValueFactory(new PropertyValueFactory<>("item"));
            category.setCellValueFactory(new PropertyValueFactory<>("category"));
            price.setCellValueFactory(new PropertyValueFactory<>("price"));
            currency.setCellValueFactory(new PropertyValueFactory<>("currency"));

            List<MenuItem> menuItemList = service.getMenuItemsService();
            ObservableList<MenuItem> menuItems = FXCollections.observableList(menuItemList);
            tableViewItems.setItems(menuItems);

            orderButton.setText("Order");
            orderButton.setOnAction(e-> handleOrderButton());
        }else{
            tabelLabel.setText("Eroare");
        }
    }

    @FXML
    private void handleOrderButton(){
        MenuItem selectedItem = tableViewItems.getSelectionModel().getSelectedItem();
        if(selectedItem!=null && currentTable != null ){
            Integer tableId = currentTable.getId_table();
            Integer itemId = selectedItem.getId_item();
            Timestamp orderDate = Timestamp.valueOf(LocalDateTime.now());
            service.save(new Order(null,tableId,itemId,orderDate, Status.PLACED));
        }else{
            System.out.println("Nicio comanda selectata.");
        }
    }
}