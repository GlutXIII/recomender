package recommendingSystem;

import controller.LoginViewController;
import controller.MenuController;
import controller.RegistrationFormController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

import static utils.StaticConstant.*;
import static utils.StaticConstant.Paths.*;

public class Main extends Application {
    public static final String TITLE = "Recommending system";
    private Stage primaryStage;
    private BorderPane rootLayout;
    public static int currentUserId;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        primaryStage.setTitle(TITLE);
        initRootLayout();
        showLoginView();
    }

    public void showLoginView(){
        LoginViewController loginViewController = (LoginViewController) initView(LOGIN_FORM);
        loginViewController.setMain(this);
    }

    public void showRegisterView(){
        RegistrationFormController registrationFormController = (RegistrationFormController) initView(REGISTRATION_FORM);
        registrationFormController.setMain(this);
    }

    public void showMenuView(){
        MenuController menuController = (MenuController)initView(MENU);
        menuController.setMain(this);
    }




    public void initRootLayout(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource(ROOT_LAYOUT));
            rootLayout = (BorderPane) loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
            primaryStage.setMinWidth(800);
            primaryStage.setMaxWidth(800);
            primaryStage.setMinHeight(650);
            primaryStage.setMaxHeight(650);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object initView(String path){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getResource(path));
            AnchorPane applicationOverview = fxmlLoader.load();

            rootLayout.setCenter(applicationOverview);
            Object o = fxmlLoader.getController();
            return o;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
