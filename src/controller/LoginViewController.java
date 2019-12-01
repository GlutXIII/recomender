package controller;
import connector.Connector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import recommendingSystem.BaseClientService;
import recommendingSystem.Main;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;


public class LoginViewController extends BaseClientService {

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

    public Connector connector = new Connector();


    @FXML
    private void initialize() {

    }

    private Main main;
    Stage dialogStage = new Stage();
    Scene scene;
    public void setMain(Main main) {
        this.main = main;
    }

    @FXML
    public void login(ActionEvent event) throws IOException {
    if (loginCheck() == true){
        main.showMenuView();
        }
    else {
        showAlert ("Failed","Recheck credentials");
    }

        if (getPasswordField().isEmpty() || getPasswordField().isEmpty()) {
            showAlert ("Recheck credentials","Login and password fields can not be empty ");

        }

    }

    @FXML
    public void openRegistration(){
        main.showRegisterView();
    }
        private boolean loginCheck () {
        connector.checkCredentials(getLoginField(),getPasswordField());
        return true;
        }


        private String getLoginField () {
            return loginField.getText();
        }

        private String getPasswordField () {
            return passwordField.getText();
        }
    }

