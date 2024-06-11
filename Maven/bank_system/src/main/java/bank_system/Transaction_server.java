package bank_system;

import java.sql.SQLException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import bank_system.supabase.PostgrestClient;
import bank_system.supabase.SupabaseClient;
import javafx.scene.control.Alert;

public class Transaction_server {

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

        SupabaseClient supabase = new SupabaseClient(API.supabaseUrl, API.supabaseKey);
        PostgrestClient postgrestClient = supabase.from("accountbalance");

        try{
            JSONObject response = postgrestClient
                .select("balance")
                .eq("account_no", account_num)
                .exec();

            JSONArray data_Array = (JSONArray) response.get("data");
            JSONObject data_Object = (JSONObject) data_Array.get(0);
            balance = (Long) data_Object.get("balance");
            float Balance = balance.floatValue();
            System.out.println("Balance: " + Balance);
            return Balance;
            
            }catch(Exception e){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Please check your connection and try again!");
                alert.show();    
                System.out.println("Balance error");   
                return 0.0f;         
        }
    }

    static void registerDepositTransaction(int account_no, float amount) {

    }

    public static void deposit(String account_nun, String deposit_amount) {

    }

    
    static void registerWithdrawTransaction(int account_no, float amount) {

    }

    static void withdraw(String account_num, String withdrawal_amoount) {

    }

    static void registerTransferTransaction(int sender_account, int reciever_account, float amount) {

    }

    static void transfer(String sender_account, String reciever_account, String transfer_amount) {
    
    }



}
