package controller;

import connector.Connector;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import recommendingSystem.BaseClientService;
import recommendingSystem.Main;

import java.io.IOException;
import java.util.List;

public class MenuController extends BaseClientService {
    public Label label;
    private Main main;

    @FXML
    public Button wyloguj;

    @FXML
    public Button find;

    @FXML
    public ListView<String> recomendationList;


    public void setMain(Main main) {
        this.main = main;
    }

    @FXML
    private void initialize() {

    }

    @FXML
    public void logout(ActionEvent event) throws IOException {
       main.showLoginView();
    }

    @FXML
    public void find(ActionEvent event) throws IOException {
        Connector connector = new Connector();
        recomendationList.setEditable(true);
        recomendationList.getItems().add(connector.getMovie(connector.getRecomendations()));
    }


}
