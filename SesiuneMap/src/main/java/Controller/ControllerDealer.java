package Controller;

import Domain.Masina;
import Domain.Status;
import Observer.Observer;
import Service.Service;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class ControllerDealer implements Observer {
    private Service service;

    @FXML
    private Button sendButton;

    @FXML
    private ListView<Masina> listViewMasini;

    public void setService(Service service) {
        this.service = service;
        this.service.addObserver(this);
        update();
        sendButton.setOnAction(event -> {;
            handleSend();
        });
    }

    @Override
    public void update(){
        listViewMasini.setItems(FXCollections.observableArrayList(service.getMasiniService()));
    }

    public void handleSend() {
        Masina masina = listViewMasini.getSelectionModel().getSelectedItem();
        if (masina != null) {
            service.updateStatusService(masina.getIdMasina(), Status.valueOf("NEEDS_APPROVAL"));
        }
    }
}
