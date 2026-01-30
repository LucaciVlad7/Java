import Controller.ControllerStaff;
import Controller.ControllerTable;
import Domain.Table;
import Repo.RepoMenuItem;
import Repo.RepoOrder;
import Repo.RepoTable;
import javafx.application.Application;
import Service.Service;
import javafx.fxml.FXMLLoader;
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
            RepoMenuItem repoMenuItem= new RepoMenuItem(connection);
            RepoTable repoTable = new RepoTable(connection);
            RepoOrder repoOrder = new RepoOrder(connection);
            service = new Service(repoTable, repoMenuItem,repoOrder);
        }catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not connect to database", e);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader staffLoader = new FXMLLoader(getClass().getResource("/staff.fxml"));
        Scene staffScene = new Scene(staffLoader.load());
        Stage staffStage = new Stage();
        staffStage.setScene(staffScene);
        staffStage.setTitle("Interfață Staff - Comenzi");
        ControllerStaff controllerStaff = staffLoader.getController();
        controllerStaff.setService(service);
        staffStage.show();

        List<Table> tables = service.getTablesService();

        for (Table t : tables) {
            FXMLLoader tableLoader = new FXMLLoader(getClass().getResource("/table.fxml"));
            Scene scene = new Scene(tableLoader.load());

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Masa: " + t.getId_table());
            ControllerTable controllerTable = tableLoader.getController();
            controllerTable.setService(service, t);
            stage.show();
        }
    }
}
