package page;

import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.net.URL;

public class LoggedInController implements Initializable {

    @FXML
    private Button logOut_button;

    @FXML
    private Label welcome_label;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        logOut_button.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                Utils.ChangeScene(event, "Log in!", "MainWindow.fxml", null);
            }
        });

    }

}
