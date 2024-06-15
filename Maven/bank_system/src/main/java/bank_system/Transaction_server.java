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
        Alert alert;
        float balance_before_withdraw = Transaction_server.checkBalance(sender_account);
        System.out.println("balance_before_withdraw" + balance_before_withdraw);
        float new_balance = balance_before_withdraw - amount;
        try{
            JSONObject newTransaction = new JSONObject();
            newTransaction.put("account_no", sender_account);
            newTransaction.put("balance", new_balance);
            PostgrestClient postgrestClient4 = supabase.from("accountbalance");
            JSONObject response = postgrestClient4
                .update(newTransaction)
                .eq("account_no", String.valueOf(sender_account))
                .exec();

            deduction_amount_flag = true;
        }catch(Exception e){
            deduction_amount_flag = false;
            alert = new Alert(AlertType.ERROR);
            alert.setContentText("CoundN't excecute your query, Please Try again");
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.showAndWait();
        }

        float balance_before_deposit = Transaction_server.checkBalance(reciever_account);   
        System.out.println("balance_before_deposit: " + balance_before_deposit);
        float new_balance2 = balance_before_deposit + amount;
        
        try{

            JSONObject newTransaction = new JSONObject();
            newTransaction.put("account_no", reciever_account);
            newTransaction.put("balance", new_balance2);
            PostgrestClient postgrestClient5 = supabase.from("accountbalance");
            JSONObject response = postgrestClient5
                .update(newTransaction)
                .eq("account_no", String.valueOf(reciever_account))
                .exec();
                add_amount_flag = true;
        }catch(Exception e){
            add_amount_flag = false;
            alert = new Alert(AlertType.ERROR);
            alert.setContentText("CoundN't excecute your query, Please Try again");
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }

    static void reverseUpdate(boolean add_amount_flag, boolean deduction_amount_flag, float amount, int sender_account, int reciever_account) {
        Alert alert;
        if (add_amount_flag == false && deduction_amount_flag == true) {
            float balance_before_withdraw = 0;
            try{
                balance_before_withdraw = Transaction_server.checkBalance(reciever_account);
                float new_balance = balance_before_withdraw - amount;
                JSONObject newTransaction = new JSONObject();
                newTransaction.put("account_no", reciever_account);
                newTransaction.put("balance", new_balance);
                JSONObject response = postgrestClient
                    .update(newTransaction)
                    .eq("account_no", String.valueOf(reciever_account))
                    .exec();

                    add_amount_flag = true;
            }catch(Exception e){
                add_amount_flag = false;
                alert = new Alert(AlertType.ERROR);
                alert.setContentText("CoundN't excecute your query, Please Try again");
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.showAndWait();
            }
        }else if (add_amount_flag == true && deduction_amount_flag == false) {
            
            try{
                float balance_before_deposit = Transaction_server.checkBalance(sender_account);
                float new_balance = balance_before_deposit + amount;
                JSONObject newTransaction = new JSONObject();
                newTransaction.put("account_no", sender_account);
                newTransaction.put("balance", new_balance);
                JSONObject response = postgrestClient
                    .update(newTransaction)
                    .eq("account_no", String.valueOf(sender_account))
                    .exec();
                    deduction_amount_flag = true;
            }catch(Exception e){
                deduction_amount_flag = false;
                alert = new Alert(AlertType.ERROR);
                alert.setContentText("CoundN't excecute your query, Please Try again");
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.showAndWait();
            }
        }
    }

    static Float checkBalance(int account_no) throws Exception {
        
        Long balance;
        String account_num = String.valueOf(account_no);


        try{
            PostgrestClient postgrestClient3 = supabase.from("accountbalance");
            JSONObject response = postgrestClient3
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
        } else if (amount > Transaction_server.max_withdraw_amaount) {
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

                Transaction_server.registerWithdrawTransaction(account_no, amount);
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
        JSONObject insertResponse = null;
        try{
        JSONObject newTransaction = new JSONObject();
        newTransaction.put("sender_account_no", sender_account);
        newTransaction.put("receiver_account_no", reciever_account);
        newTransaction.put("amount", amount);

        insertResponse = postgrestClient_Transaction.insert(newTransaction).exec();
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please check your connection and try again!");
            alert.show(); 
        }
    }

    static void transfer(String sender_account, String reciever_account, String transfer_amount) {
        Alert alert;
        Transaction_server.add_amount_flag = true;
        Transaction_server.deduction_amount_flag = true;
        int s_acount = Integer.parseInt(sender_account), r_account;
        float amount = 0;
        float balance_before_transfer = 0, balance_after_transfer = 0;

        try {
            balance_before_transfer = Transaction_server.checkBalance(s_acount);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (InputValidator.isFloat(transfer_amount)) {
            amount = Float.parseFloat(transfer_amount);
            if (InputValidator.isInteger(reciever_account)) {
                r_account = Integer.parseInt(reciever_account);
                System.out.println(reciever_account);

                if (amount > balance_before_transfer) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setContentText("Insufficient balance");
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText(null);
                    alert.showAndWait();
                }

                else if (amount > Transaction_server.max_transfer_amaount) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setContentText("The amount is beyond the limit");
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText(null);
                    alert.showAndWait();
                }
                else{
                    boolean check;
                    try {
                        PostgrestClient postgrestClient2 = supabase.from("accountbalance");
                        JSONObject response = postgrestClient2
                        .select("*")
                        .eq("account_no", reciever_account)
                        .exec();
                        JSONArray data_Array = (JSONArray) response.get("data");
                        System.out.println("Response" + response);
                        try  {
                            check = data_Array.size() > 0;
                            System.out.println("r_account " + r_account);
                            System.out.println("boolean: " + check);

                            // check where the entered account esist in db or not
                            // if(check) System.out.println("true");
                            if (check) {

                                Transaction_server.update(amount, s_acount, r_account, Transaction_server.add_amount_flag,
                                        Transaction_server.deduction_amount_flag);

                                if (Transaction_server.add_amount_flag == false
                                        || Transaction_server.deduction_amount_flag == false) {
                                    Transaction_server.reverseUpdate(Transaction_server.add_amount_flag,
                                            Transaction_server.deduction_amount_flag, amount, s_acount, r_account);
                                    // check=false;
                                } else {
                                    balance_after_transfer = Transaction_server.checkBalance(s_acount);
                                    Transaction_server.registerTransferTransaction(s_acount, r_account, amount);

                                    alert = new Alert(AlertType.INFORMATION);
                                    alert.setContentText(
                                            "   balance before transfer =" + balance_before_transfer + "\n" +
                                                    "   balance after transfer =" + balance_after_transfer);
                                    alert.setTitle("Information Dialog");
                                    alert.setHeaderText("Successfully Transfered");


                                    alert.getButtonTypes().setAll(ButtonType.OK);
                                    Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
                                    okButton.setOnAction(e -> {
                                        // Perform your action here
                                        Transaction_server.transferController.cancelWith_button.fire();
                                    });

                                    alert.showAndWait();

                                }
                            } else {
                                alert = new Alert(AlertType.ERROR);
                                alert.setContentText("there is no account " + r_account + " registered in the database");
                                alert.setTitle("Information Dialog");
                                alert.setHeaderText(null);
                                alert.showAndWait();
                            }
                        } catch (Exception e) {

                            alert = new Alert(AlertType.ERROR);
                            alert.setContentText("Error on fetching data from the database, Please Try Again");
                            alert.setTitle("Information Dialog");
                            alert.setHeaderText(null);
                            alert.showAndWait();
                        }
                    } catch (Exception e) {

                        alert = new Alert(AlertType.ERROR);
                        alert.setContentText("Couldn't connect to database, Please Try Again");
                        alert.setTitle("Information Dialog");
                        alert.setHeaderText(null);
                        alert.showAndWait();
                    }
                }

            } else {
                alert = new Alert(AlertType.ERROR);
                alert.setContentText("Error, Please Enter a valid number");
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Invalid Input");
                alert.showAndWait();
            }
        } else {
            alert = new Alert(AlertType.ERROR);
            alert.setContentText("Error, Please Enter a valid number");
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Invalid Input");
            alert.showAndWait();
        }

    }
                }

