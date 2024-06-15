package bank_system;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.List;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.RowConstraints;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

 
public class RecentTransactionController implements Initializable {

    @FXML
    private TableView<RecTransaction> recent_table;

    @FXML
    private TableColumn<RecTransaction, Long> amount_column;

    @FXML
    private Button home_button;

    @FXML
    private TableColumn<RecTransaction, Long> id_column;

    @FXML
    private TableColumn<RecTransaction, String> tDate_column;

    @FXML
    private TableColumn<RecTransaction, String> tType_column;

    String Email = Utils.Email;
    String account_no = Customer_server.Account_no_retriver(Email);
    private Transaction_server transactionServer;
    ObservableList<RecTransaction> transactionList = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL location, ResourceBundle resources){
        transactionServer = new Transaction_server();
        transactionServer.Depo_With(account_no);
        transactionServer.Transfer_Depo(account_no);
        transactionServer.Transfer_Withdraw(account_no);
        JSONArray merged_data = transactionServer.merge_array();
        
        for (int i = 0; i < merged_data.size(); i++) {
            JSONObject data_Object = (JSONObject) merged_data.get(i);
            Long id = (Long) data_Object.get("transaction_id");
            int Int_id = id.intValue();
            Long amount = (Long) data_Object.get("amount");
            int Int_amount = amount.intValue();
            String date = (String) data_Object.get("transaction_date");
            String type = (String) data_Object.get("Type");
            LocalDateTime transactionDateTime = LocalDateTime.parse(date);

            String formattedTransactionDate = transactionDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            transactionList.add(new RecTransaction(Int_id, Int_amount, formattedTransactionDate, type));
        }
    
        id_column.setCellValueFactory(new PropertyValueFactory<>("id"));
        amount_column.setCellValueFactory(new PropertyValueFactory<>("amount"));
        tDate_column.setCellValueFactory(new PropertyValueFactory<>("date"));
        tType_column.setCellValueFactory(new PropertyValueFactory<>("type"));
        
        
        recent_table.setItems(transactionList);
    }
}

