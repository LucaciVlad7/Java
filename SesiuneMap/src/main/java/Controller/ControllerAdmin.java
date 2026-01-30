package Controller;

import Domain.Masina;
import Service.Service;
import javafx.collections.FXCollections;
import Observer.Observer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class ControllerAdmin implements Observer {
    private Service service;

    @FXML
    private ListView<Masina> listViewPtAdmin;

    @FXML
    private Button approveButton;

    @FXML
    private Button rejectButton;

    public void setService(Service service){
        this.service = service;
        this.service.addObserver(this);
        update();
        approveButton.setOnAction(e -> {handleApprove();});
        rejectButton.setOnAction(e -> {handleReject();});
    }

    @Override
    public void update() {
        listViewPtAdmin.setItems(FXCollections.observableArrayList(service.getMasiniForApprovalService()));
    }

    private void handleApprove(){
        Masina selectedMasina = listViewPtAdmin.getSelectionModel().getSelectedItem();
        if(selectedMasina != null){
            service.updateStatusService(selectedMasina.getIdMasina(), Domain.Status.APPROVED);
        }
    }

    private void handleReject(){
        Masina selectedMasina = listViewPtAdmin.getSelectionModel().getSelectedItem();
        if(selectedMasina != null){
            service.updateStatusService(selectedMasina.getIdMasina(), Domain.Status.REJECTED);
        }
    }
}
