package bank_system;

import org.json.JSONArray;
import org.json.simple.JSONObject;

import bank_system.supabase.GoTrue;
import bank_system.supabase.PostgrestClient;
import bank_system.supabase.SupabaseClient;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

public class Customer_server{
    public static void SignIn(ActionEvent event, String fName, String lName,String age, String email, String password, String gender, String tellNo, String account_type){
        SupabaseClient supabase = new SupabaseClient(API.supabaseUrl, API.supabaseKey);
        PostgrestClient postgrestClient = supabase.from("customer");
        JSONObject response = null;

        // This checks the connection to the server
        try{
            response = postgrestClient
                    .select("email")
                    .eq("email", email)
                    .exec();
            
            
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Sorry Connection to the could not be performed.");
            alert.setHeaderText("Connection Error");
            alert.show();
        }

        // This check weather email already exists or not
        try{
            JSONArray data_Array = (JSONArray) response.get("data");
            JSONObject email_Object = (JSONObject) data_Array.get(0);

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("The email already exists please insert a valid email");
            alert.show();
        }catch(Exception e){
            JSONObject insertResponse = null;

            // This try-catch is to validate the constraints on the database
            try{
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
                
                // This is not the correct place to authorize the user. Fix this

                // Authorization 
                // GoTrue auth = supabase.auth;
                // auth.signUp(email, password);


                Utils.ChangeScene(event, "Created Account", "LoggedIn.fxml", email);
            }
            catch(Exception exp){
                JSONObject error_object = (JSONObject) insertResponse.get("error");
                String errorMessage = (String) error_object.get("message");

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText(errorMessage);
                    alert.setHeaderText("Check if you typed your email in the correct format");
                    alert.show();
                    
            }
        }
    }

    public static void LogIn(ActionEvent event, String email, String password){

    }

    public static String Account_no_retriver(String email){
        return "";
    }
}