package bank_system;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TransferController implements Initializable {

    @FXML
    private TextField amount_lineEdit;

    @FXML
    public Button cancelWith_button;

    @FXML
    private Button home_button2;

    @FXML
    private TextField recieverAcc_lineEdit;

    @FXML
    private Button transferDB_button;

    String Email = Utils.Email;
    String account_no = Customer_server.Account_no_retriver(Email);

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Transactions.transferController = this;
        Transaction_server.transferController = this;

        transferDB_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Transaction_server.transfer(account_no, recieverAcc_lineEdit.getText(), amount_lineEdit.getText());
            }
        });

        cancelWith_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = (Stage) cancelWith_button.getScene().getWindow();
                stage.close();
            }
        });

        home_button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = (Stage) home_button2.getScene().getWindow();
                stage.close();
            }
        });
    }

}