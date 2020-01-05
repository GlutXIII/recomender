package controller;

import connector.Connector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.DaoService;
import recommendingSystem.BaseClientService;
import recommendingSystem.Main;

public class RegistrationFormController extends BaseClientService {
    ObservableList<String> genderList = FXCollections.observableArrayList("M","F");
    ObservableList<String> occupationList = FXCollections.observableArrayList("academic/educator","artist","clerical/admin","college/grad student", "customer service","doctor/health care","executive/managerial","farmer","homemaker","K-12 student", "lawyer","programmer","retired", "sales/marketing","scientist","self-employed", "technician/engineer","tradesman/craftsman","unemployed", "writer","other");


    @FXML
    public TextField loginField;
    @FXML
    public TextField passwordField;
    @FXML
    public Button signUpButton;
    @FXML
    public ComboBox occupationComboBox;
    @FXML
    public ComboBox genderComboBox;
    @FXML
    public TextField ageField;
    @FXML
    public Button goBack;


    @FXML
    private void initialize() {
    occupationComboBox.setItems(occupationList);
    genderComboBox.setItems(genderList);
    }

    private DaoService daoService = new DaoService();

    private Main main;
    Stage dialogStage = new Stage();
    Scene scene;
    public void setMain(Main main) {
        this.main = main;
    }



    @FXML
    public void registerUser(){
        if(daoService.registerCheck(getLoginField(), getPasswordField(), getGender(), getOccupation(), getAgeField()))
        {
         showAlert("Success","Account succesfully created");
         main.showLoginView();
        }
        else{
            showAlert("Failed","Something went wrong");
        }
    }

    @FXML
    public void goBack(){
        main.showLoginView();
    }


    private String getLoginField () {
        return loginField.getText();
    }

    private String getPasswordField () {
        return passwordField.getText();
    }

    private String getAgeField () {
        return ageField.getText();
    }

    private String getGender(){
        return (String) genderComboBox.getValue();
    }

    private String getOccupation(){
        return (String) occupationComboBox.getValue();
    }


}
