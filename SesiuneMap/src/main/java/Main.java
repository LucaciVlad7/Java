import Controller.ControllerLogIn;
import Domain.Admin;
import Domain.Dealer;
import Repo.RepoAdmin;
import Repo.RepoDelear;
import Repo.RepoMasina;
import javafx.application.Application;
import Service.Service;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Main extends Application {
    private Service service;

    @Override
    public void init() throws Exception {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String password = "BazaDateVlad7";

        try{
            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the database");
            RepoAdmin repoAdmin = new RepoAdmin(connection);
            RepoDelear repoDelear = new RepoDelear(connection);
            RepoMasina repoMasina = new RepoMasina(connection);
            service = new Service(repoAdmin, repoDelear,repoMasina);
        }catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not connect to database", e);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        for (Admin admin : service.getAdminsService()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/logIn.fxml"));
            Parent root = loader.load();

            ControllerLogIn controller = loader.getController();
            controller.setService(service);

            Stage stage = new Stage();
            stage.setTitle("Pagina logare");
            stage.setScene(new Scene(root));
            stage.show();
        }

        for (Dealer dealer : service.getDealersService()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/logIn.fxml"));
            Parent root = loader.load();

            ControllerLogIn controller = loader.getController();
            controller.setService(service);

            Stage stage = new Stage();
            stage.setTitle("Pagina logare");
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
