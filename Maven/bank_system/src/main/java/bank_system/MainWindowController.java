package bank_system;

import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.net.URL;

public class MainWindowController implements Initializable {

    @FXML
    private Button admin_button;

    @FXML
    private TextField email_lineEdit;

    @FXML
    private Button login_button;

    @FXML
    private TextField password_lineEdit;

    @FXML
    private Button sign_button;

    @Override
    public void initialize(URL location, ResourceBundle resources){

        login_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                Customer_server.LogIn(event, email_lineEdit.getText(), password_lineEdit.getText());
            }
        });

        admin_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                Utils.ChangeScene(event, "Adminstrator", "Admin.fxml", null);
            }
        });

        sign_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                Utils.ChangeScene(event, "Create account", "SignIn.fxml", null);
            }
        });
    }

}
