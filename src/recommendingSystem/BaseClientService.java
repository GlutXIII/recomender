package recommendingSystem;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import java.io.IOException;
import java.util.Optional;

public class BaseClientService {


    protected void showAlert(String title, String info){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle(title);
        alert.setContentText(info);
        alert.showAndWait();
    }

    protected void initView(BorderPane borderPane, Object o, String path) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(path));
            fxmlLoader.setController(o);
            AnchorPane anchorPane = fxmlLoader.load();
            borderPane.setCenter(anchorPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
