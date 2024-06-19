package bank_system;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


import bank_system.supabase.GoTrue;
import bank_system.supabase.PostgrestClient;
import bank_system.supabase.SupabaseClient;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

public class Customer_server {
    public static void SignIn(ActionEvent event, String fName, String lName, String age, String email, String password,
            String gender, String tellNo, String account_type) {
        SupabaseClient supabase = new SupabaseClient(API.supabaseUrl, API.supabaseKey);
        PostgrestClient postgrestClient = supabase.from("customer");
        try {
            JSONObject response = postgrestClient
                    .select("*")
                    .eq("email", email)
                    .exec();

            JSONArray data_Array = (JSONArray) response.get("data");

            // This check weather email already exists or not
            if (data_Array.size() != 0) {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("The email already exists please insert a valid email");
                alert.show();

            } else {
                JSONObject insertResponse = null;

                GoTrue auth = supabase.auth;
                auth.signUp(email, password);

                JSONObject newCustomer = new JSONObject();
                newCustomer.put("password", password);
                newCustomer.put("account_type", account_type);
                newCustomer.put("gender", gender);
                newCustomer.put("last_name", lName);
                newCustomer.put("first_name", fName);
                newCustomer.put("age", age);
                newCustomer.put("email", email);
                newCustomer.put("tell_no", tellNo);

                insertResponse = postgrestClient.insert(newCustomer).exec();
                
                JSONObject error_object = (JSONObject) insertResponse.get("error");
                if (error_object == null) {

                    JSONArray data_Array2 = (JSONArray) insertResponse.get("data");
                    JSONObject data_Object = (JSONObject) data_Array2.get(0);
                    Long Account_no = (Long) data_Object.get("account_no");
                    int account_num = Math.toIntExact(Account_no);                    
                                        
                    try{         
                    PostgrestClient postgrestClient2 = supabase.from("accountbalance");

                    JSONObject insertAccount = null;
                    JSONObject newAccount = new JSONObject();
                    newAccount.put("account_no", account_num);
                    newAccount.put("balance", 0.0);

                    insertAccount = postgrestClient2.insert(newAccount).exec();
                    
                }catch(Exception ex){
                    System.out.println("Account balance error");
                }
                    // Authorization
                    // GoTrue auth = supabase.auth;
                    // auth.signUp(email, password);

                    Utils.ChangeScene(event, "Created Account", "LoggedIn.fxml", email);
                } else {

                    String errorMessage = (String) error_object.get("message");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    if (errorMessage.contains("\"chk_email\"")){
                        alert.setContentText("The email you provided is not in the correct format. It should be Example@gmail.com");
                        alert.setHeaderText("Check if you typed your everything in the correct format");
                        alert.show();
                    }else if(errorMessage.contains("\"chk_age\"")){
                        alert.setContentText("You are under age. You must be above 18 to create an account here.");
                        alert.setHeaderText("Check if you typed your everything in the correct format");
                        alert.show();
                    }else if(errorMessage.contains("\"chk_tellno\"")){
                        alert.setContentText("The phone number must be 10 digits.");
                        alert.setHeaderText("Check if you typed your everything in the correct format");
                        alert.show();
                    }
                    else{
                        alert.setContentText(errorMessage);
                        alert.setHeaderText("Check if you typed your everything in the correct format");
                        alert.show();
                    }
                }

            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please check your connection and try again!");
            alert.show();
        }
    }

    public static void LogIn(ActionEvent event, String email, String password) {

        SupabaseClient supabase = new SupabaseClient(API.supabaseUrl, API.supabaseKey);
        PostgrestClient postgrestClient = supabase.from("customer");

        try{
            JSONObject response = postgrestClient
                    .select("*")
                    .eq("email", email)
                    .exec();

            JSONArray data_Array = (JSONArray) response.get("data");

            // This check weather email already exists or not
            if (data_Array.size() == 0) {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Invalid credentials please try again");
                alert.show();

            }else{

                JSONArray data_Array2 = (JSONArray) response.get("data");
                JSONObject data_Object = (JSONObject) data_Array2.get(0);
                String retrived_password = (String) data_Object.get("password");
                if(retrived_password.equals(password)){
                    Utils.ChangeScene(event, "Logged in" ,"LoggedIn.fxml", email);
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Incorrect password. Please try again.");
                    alert.show();
                }
            }
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please check your connection and try again!");
            alert.show();
        }
    }

    public static String Account_no_retriver(String email) {

        SupabaseClient supabase = new SupabaseClient(API.supabaseUrl, API.supabaseKey);
        PostgrestClient postgrestClient = supabase.from("customer");

        String Account_no = null; // change the account number to string; makes it easier for transaction class

        try{
            JSONObject response = postgrestClient
                    .select("*")
                    .eq("email", email)
                    .exec();

            JSONArray data_Array = (JSONArray) response.get("data");

            // This check weather email already exists or not
            if (data_Array.size() == 0) {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Invalid Email address please try again");
                alert.show();

            }else{

                JSONArray data_Array2 = (JSONArray) response.get("data");
                JSONObject data_Object = (JSONObject) data_Array2.get(0);
                Long account_no = (Long) data_Object.get("account_no");
                Account_no = String.valueOf(account_no);
                
            }
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please check your connection and try again!");
            alert.show();
        }

        return Account_no;
    }

    public static ArrayList<Integer> gender_counter(){
        ArrayList<Integer> maleFemale = new ArrayList<>();
        SupabaseClient supabase = new SupabaseClient(API.supabaseUrl, API.supabaseKey);
        PostgrestClient postgrestClient5 = supabase.from("customer");
        JSONObject response2 = postgrestClient5
            .select("gender")
            .exec();
        
        JSONArray gender = (JSONArray) response2.get("data");
        int male= 0;
        int female = 0;
        for (int i = 0; i< gender.size(); i++){
            JSONObject counter = (JSONObject) gender.get(i);
            String value = (String) counter.get("gender");
            try{
                if (value.equals("Male")) male++;
                else female++;
            }catch(NullPointerException e){
                
            }
        }
        maleFemale.add(male);
        maleFemale.add(female);

        return maleFemale;
    }

    public static ArrayList<Integer> AccType_counter(){
        ArrayList<Integer> type = new ArrayList<>();
        SupabaseClient supabase = new SupabaseClient(API.supabaseUrl, API.supabaseKey);
        PostgrestClient postgrestClient5 = supabase.from("customer");
        JSONObject response2 = postgrestClient5
            .select("account_type")
            .exec();
        
        JSONArray gender = (JSONArray) response2.get("data");
        int checking= 0;
        int saving = 0;
        int business = 0;
        for (int i = 0; i< gender.size(); i++){
            JSONObject counter = (JSONObject) gender.get(i);
            String value = (String) counter.get("account_type");
            if (value.equals("Checking Account")) checking++;
            else if (value.equals("Savings Account")) saving++;
            else business++;
        }
        type.add(checking);
        type.add(saving);
        type.add(business);

        return type;
    }
}