package Controller;

import Domain.Admin;
import Domain.Dealer;
import Domain.User;
import Service.Service;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLOutput;

public class ControllerLogIn {
    private Service service;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button loginButton;

    public void setService(Service service){
        this.service=service;
        loginButton.setOnAction(event -> {handleLogin();});
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        User user = service.login(username, password);

        if (user != null) {
            if (user instanceof Dealer) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/dealer.fxml"));
                    Parent root = loader.load();

                    ControllerDealer controller = loader.getController();
                    controller.setService(service);

                    Stage stage = new Stage();
                    stage.setTitle("Dealer: " + user.getUsername());
                    stage.setScene(new Scene(root));
                    stage.show();

                    ((Stage) loginButton.getScene().getWindow()).close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (user instanceof Admin) {
                try {
                    FXMLLoader loaderAdmin = new FXMLLoader(getClass().getResource("/admin.fxml"));
                    Parent root = loaderAdmin.load();

                    ControllerAdmin controller = loaderAdmin.getController();
                    controller.setService(service);

                    Stage stage = new Stage();
                    stage.setTitle("Dealer: " + user.getUsername());
                    stage.setScene(new Scene(root));
                    stage.show();

                    ((Stage) loginButton.getScene().getWindow()).close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("Invalid Login");
        }
    }
}
