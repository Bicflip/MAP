package org.example.a4map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.example.a4map.controller.Controller;
import org.example.a4map.domain.Entity;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    private ObservableList<ArrayList<Entity>> observableLista;
    private ArrayList<Entity> lista;
    private Controller controller;

    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }


    public HelloController(){
        this.controller = controller;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        observableLista = FXCollections.observableArrayList();
        lista = new ArrayList<>();

        controller.loadData();
        this.controller.sortById();
        lista = this.controller.getAll();
        this.observableLista.addAll(lista);
        this.EntitatiListView.setItems(observableLista);
    }

}