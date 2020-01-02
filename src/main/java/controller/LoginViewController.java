package controller;

import connector.Connector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.DaoService;
import recommendingSystem.BaseClientService;
import recommendingSystem.Main;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;


public class LoginViewController extends BaseClientService {

    private Connector connector = new Connector();

    private DaoService daoService = new DaoService();

    private Main main;
    Stage dialogStage = new Stage();
    Scene scene;

    @FXML
    public PasswordField passwordField;

    @FXML
    public TextField loginField;

    @FXML
    public Label informationLabel;

    @FXML
    public Button loginButton;

    @FXML
    public Button registerButton;

    @FXML
    private void initialize() {

    }

    @FXML
    public void login(ActionEvent event) throws IOException {
        if (daoService.checkCredentials(getLoginField(), getPasswordField())) {
            showAlert("Success", "Welcome to recommending system");
            main.showMenuView();
        } else {
            showAlert("Failed", "Recheck credentials");
        }

        if (getPasswordField().isEmpty() || getPasswordField().isEmpty()) {
            showAlert("Recheck credentials", "Login and password fields can not be empty ");

        }

    }

    @FXML
    public void openRegistration() {
        main.showRegisterView();
    }

    private String getLoginField() {
        return loginField.getText();
    }

    private String getPasswordField() {
        return passwordField.getText();
    }

    public void setMain(Main main) {
        this.main = main;
    }
}

