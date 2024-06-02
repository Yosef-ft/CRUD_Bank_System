package bank_system;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class chapaController implements Initializable {

    @FXML
    private TextField Lname_lineEdit;

    @FXML
    private TextField amount_lineEdit;

    @FXML
    private TextField email_lineEdit;

    @FXML
    private TextField fname_lineEdit;

    @FXML
    private Button home_button2;

    @FXML
    private Button link_button;

    @FXML
    private WebView web_view;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        link_button.setOnAction(event -> {
            String checkout_url = ChapaConnection.payment(email_lineEdit.getText(), fname_lineEdit.getText(), Lname_lineEdit.getText(), amount_lineEdit.getText()).getAsJsonObject("detail").getAsJsonObject("data").get("checkout_url").getAsString();
            ChapaConnection.checkout_url = checkout_url;
            web_view.getEngine().load(checkout_url);
        });
    
        home_button2.setOnAction(event -> {
            String Email = LoggedInController.Email;
            Utils.ChangeScene(event, "Logged in", "LoggedIn.fxml", Email);
        });
    
    }

}
