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
import javafx.scene.web.WebView;

public class chapaController implements Initializable {

    @FXML
    private Button link;

    @FXML
    private WebView web_view;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        String checkout_url = ChapaConnection.openCheckoutUrl();
        link.setOnAction(event -> web_view.getEngine().load(checkout_url));
        
    }
    

}
