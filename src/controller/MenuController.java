package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import recommendingSystem.BaseClientService;
import recommendingSystem.Main;

public class MenuController extends BaseClientService {
    public Label label;
    public Label dlaPiotrka;
    private Main main;
    public void setMain(Main main) {
        this.main = main;
    }

    @FXML
    private void initialize() {

    }


}
