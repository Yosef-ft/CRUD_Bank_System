package bank_system;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class WithdrawController implements Initializable {

    @FXML
    private Button WithdrawDB_button;

    @FXML
    private TextField amount_lineEdit;

    @FXML
    public Button cancelWith_button;

    @FXML
    private Button home_button2;

    String Email = Utils.Email;
    String account_no = Customer_server.Account_no_retriver(Email);

    @Override
    public void initialize(URL location, ResourceBundle resources){

        Transactions.withdrawController = this;
        Transaction_server.withdrawController = this;

        cancelWith_button.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                Stage stage = (Stage) cancelWith_button.getScene().getWindow();
                stage.close();
            }
        });

        WithdrawDB_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                Transaction_server.withdraw(account_no, amount_lineEdit.getText());
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
