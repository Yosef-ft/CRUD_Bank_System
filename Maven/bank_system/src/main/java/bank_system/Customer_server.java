package bank_system;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.google.gson.JsonObject;

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
                    .select("email")
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
                    
                    // Authorization
                    GoTrue auth = supabase.auth;
                    auth.signUp(email, password);

                    Utils.ChangeScene(event, "Created Account", "MainWindow.fxml", email);
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
        return "";
    }
}