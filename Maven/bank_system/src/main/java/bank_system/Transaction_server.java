package bank_system;

import java.sql.SQLException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import bank_system.supabase.PostgrestClient;
import bank_system.supabase.SupabaseClient;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

public class Transaction_server {

    static SupabaseClient supabase = new SupabaseClient(API.supabaseUrl, API.supabaseKey);
    static PostgrestClient postgrestClient = supabase.from("accountbalance");
    static PostgrestClient postgrestClient_Transaction = supabase.from("transactions");

    static DepositController depositController;
    static WithdrawController withdrawController;
    static TransferController transferController;

    static float max_withdraw_amaount = 5000;
    static float max_transfer_amaount = 50000;
    static boolean add_amount_flag, deduction_amount_flag;

    static void update(float amount, int sender_account, int reciever_account, boolean add_amount_flag, boolean deduction_amount_flag) throws Exception {

    }

    static void reverseUpdate(boolean add_amount_flag, boolean deduction_amount_flag, float amount, int sender_account, int reciever_account) {

    }

    static Float checkBalance(int account_no) throws Exception {
        
        Long balance;
        String account_num = String.valueOf(account_no);


        try{
            JSONObject response = postgrestClient
                .select("balance")
                .eq("account_no", account_num)
                .exec();

            JSONArray data_Array = (JSONArray) response.get("data");
            JSONObject data_Object = (JSONObject) data_Array.get(0);
            balance = (Long) data_Object.get("balance");
            float Balance = balance.floatValue();
            return Balance;
            
            }catch(Exception e){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Please check your connection and try again!");
                alert.show();     
                return 0.0f;         
        }
    }

    static void registerDepositTransaction(int account_no, float amount) {
        JSONObject insertResponse = null;
        try{
        JSONObject newTransaction = new JSONObject();
        newTransaction.put("sender_account_no", account_no);
        newTransaction.put("amount", amount);

        insertResponse = postgrestClient_Transaction.insert(newTransaction).exec();
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please check your connection and try again!");
            alert.show(); 
        }
    }

    public static void deposit(String account_num, String deposit_amount) {
        Alert alert;
        float amount = 0;
        float balance_before_deposit;
        float balance_after_deposit;
        int account_no = Integer.parseInt(account_num);

        try {
            balance_before_deposit = Transaction_server.checkBalance(account_no);
        } catch (Exception e) {
            balance_before_deposit =  0;
            e.printStackTrace();
        }

        if (InputValidator.isFloat(deposit_amount) || deposit_amount == "") {
            amount = Float.parseFloat(deposit_amount);
            
            float new_balance = balance_before_deposit + amount;
            System.out.println(new_balance);


            try{
                JSONObject newTransaction = new JSONObject();
                newTransaction.put("account_no", account_no);
                newTransaction.put("balance", new_balance);
                JSONObject response = postgrestClient
                    .update(newTransaction)
                    .eq("account_no", account_num)
                    .exec();
                    System.out.println(response);
                    System.out.println("Transaction insert: " + response);
                balance_after_deposit = Transaction_server.checkBalance(account_no);
                System.out.println("before: " + balance_before_deposit);

                // showinhg the befor and after balances
                alert = new Alert(AlertType.INFORMATION);
                alert.setContentText("balance before deposit =" + balance_before_deposit + "\n" +
                        "balance after deposit =" + balance_after_deposit);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Succesfully Deposited");

                Transaction_server.registerDepositTransaction(account_no, amount);
                alert.getButtonTypes().setAll(ButtonType.OK);
                Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
                okButton.setOnAction(e -> {
                    // Perform your action here
                    Transaction_server.depositController.cancelDepo_button.fire();
                });
                alert.showAndWait();
                
            }catch(Exception e){
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Please check your connection and try again!");
                alert.show(); 
            }

            }
    }

    
    static void registerWithdrawTransaction(int account_no, float amount) {
        amount *= -1;
        JSONObject insertResponse = null;
        try{
        JSONObject newTransaction = new JSONObject();
        newTransaction.put("sender_account_no", account_no);
        newTransaction.put("amount", amount);

        insertResponse = postgrestClient_Transaction.insert(newTransaction).exec();
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please check your connection and try again!");
            alert.show(); 
        }
    }

    static void withdraw(String account_num, String withdrawal_amoount) {

        float amount = 0, balance_before_withdraw = 0;
        float balance_after_withdraw = 0;
        int account_no = Integer.parseInt(account_num);
        Alert alert;

        try {
            balance_before_withdraw = Transaction_server.checkBalance(account_no);
        } catch (Exception e) {
            balance_before_withdraw =  0;
            e.printStackTrace();
        }

        if (InputValidator.isFloat(withdrawal_amoount) || withdrawal_amoount == "") {
            amount = Float.parseFloat(withdrawal_amoount);
        }

        if (amount > balance_before_withdraw) {
            alert = new Alert(AlertType.ERROR);
            alert.setContentText("Please try Again with a smaller amount");
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Insufficient balance!");
            alert.showAndWait();
        } else if (amount > Transactions.max_withdraw_amaount) {
            alert = new Alert(AlertType.ERROR);
            alert.setContentText("The amount is beyond the limit, Try Again");
            alert.setTitle("Information Dialog");
            alert.setHeaderText("null");
            alert.showAndWait();
        } else {
            float new_balance = balance_before_withdraw - amount;
            System.out.println(new_balance);


            try{
                JSONObject newTransaction = new JSONObject();
                newTransaction.put("account_no", account_no);
                newTransaction.put("balance", new_balance);
                JSONObject response = postgrestClient
                    .update(newTransaction)
                    .eq("account_no", account_num)
                    .exec();
                    System.out.println(response);
                    System.out.println("Transaction insert: " + response);
                    balance_after_withdraw = Transaction_server.checkBalance(account_no);
                System.out.println("before: " + balance_before_withdraw);

                // showinhg the befor and after balances
                alert = new Alert(AlertType.INFORMATION);
                alert.setContentText("balance before withdraw =" + balance_before_withdraw + "\n" +
                        "balance after withdraw =" + balance_after_withdraw);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Succesfully withdrawal!");

                Transaction_server.registerDepositTransaction(account_no, amount);
                alert.getButtonTypes().setAll(ButtonType.OK);
                Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
                okButton.setOnAction(e -> {
                    // Perform your action here
                    Transaction_server.withdrawController.cancelWith_button.fire();
                });
                alert.showAndWait();
                
            }catch(Exception e){
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Please check your connection and try again!");
                alert.show(); 
            }

        }

    }

    static void registerTransferTransaction(int sender_account, int reciever_account, float amount) {

    }

    static void transfer(String sender_account, String reciever_account, String transfer_amount) {
    
    }



}
