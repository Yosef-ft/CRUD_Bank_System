package page;

import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import java.net.URL;

public class SignInController implements Initializable {

    @FXML
    private TextField FName_lineEdit;

    @FXML
    private Button create_button;

    @FXML
    private TextField age_lineEdit;

    @FXML
    private TextField email_lineEdit;

    @FXML
    private ComboBox<String> gender_comboBox;

    @FXML
    private TextField lName_lineEdit;

    @FXML
    private Button login_button;

    @FXML
    private TextField pass_lineEdit;

    @FXML
    private TextField telNo_lineEdit;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        gender_comboBox.setItems(FXCollections.observableArrayList("Male", "Female"));
        
        create_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                customer.SignIn(event, FName_lineEdit.getText(), lName_lineEdit.getText(), age_lineEdit.getText(),email_lineEdit.getText(), pass_lineEdit.getText(), gender_comboBox.getValue(),telNo_lineEdit.getText());
            }
        });

        login_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                Utils.ChangeScene(event, "Login", "MainWindow.fxml", null);
            }
        });
    }

}
