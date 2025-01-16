package org.example.a4map;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.a4map.controller.Controller;
import org.example.a4map.domain.Entity;
import org.example.a4map.repo.DBRepo;
import org.example.a4map.service.Service;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        try{
        DBRepo repo = new DBRepo<>(Entity.class);
        Service service = new Service(repo);
        Controller controller = new Controller(service);


        HelloController helloController = new HelloController();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch();
    }
}