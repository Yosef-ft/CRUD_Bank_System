package bank_system;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.net.URL;
import java.util.ResourceBundle;

public class DepositController implements Initializable {

    @FXML
    public Button cancelDepo_button;

    @FXML
    private Button depositDB_button;

    @FXML
    private Button home_button2;

    @FXML
    private TextField amount_lineEdit;
    
    String Email = Utils.Email;
    String account_no = customer.Account_no_retriver(Email);

    @Override
    public void initialize(URL location, ResourceBundle resources){

        Transactions.depositController = this;

        cancelDepo_button.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                Stage stage = (Stage) cancelDepo_button.getScene().getWindow();
                stage.close();
            }
        });

        depositDB_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                Transactions.deposit(account_no, amount_lineEdit.getText());
            }
        });

        home_button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                Stage stage = (Stage) home_button2.getScene().getWindow();
                stage.close();
            }
        });
    }
}
