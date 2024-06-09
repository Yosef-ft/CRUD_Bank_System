package bank_system;

import java.io.IOException;
import org.json.simple.JSONObject;


import bank_system.supabase.PostgrestClient;
import bank_system.supabase.SupabaseClient;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;

public class Utils extends Application{
    public static String Email;

    public static void ChangeScene(ActionEvent event, String title, String fxmlFile, String email){
        Parent root = null;
        Email = email;

        if(email != null){
            try{
                FXMLLoader loader = new FXMLLoader(Utils.class.getResource(fxmlFile));
                root = loader.load();
                //LoggedInController loggedIn = loader.getController();

            }catch(IOException e){
                e.printStackTrace();
            }
        }else{
            try{
                root = FXMLLoader.load(Utils.class.getResource(fxmlFile));
            }catch(IOException e){
                e.printStackTrace();
            }
        }

        if (fxmlFile == "linkOpener.fxml"){
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle(title);
            stage.setScene(new Scene(root, 1200,650));
            stage.show();
        }else{
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root, 600, 450));
        stage.show();
        }
    }

    @Override
    public void start(Stage PrimaryStage){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
            Scene scene = new Scene(root);

            PrimaryStage.setScene(scene);
            PrimaryStage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);    
        // SupabaseClient supabase = new SupabaseClient(API.supabaseUrl, API.supabaseKey);

        // PostgrestClient postgrestClient = supabase.from("customer");

       // Example: Select query with filters and ordering
        // JSONObject response = postgrestClient
        //     .select("*")
        //     .eq("email", "yosef@gmail.com")
        //     // .order("created_at")
        //     .limit(10)
        //     .exec();
        //     System.out.println(response);

        // JSONObject newCustomer = new JSONObject();
        // newCustomer.put("password", "1234");
        // newCustomer.put("account_type", "Regular");
        // newCustomer.put("gender", "Male");
        // newCustomer.put("last_name", "Fetene");
        // newCustomer.put("first_name", "Yosef");
        // newCustomer.put("age", 25);
        // newCustomer.put("email", "yosef@gmail.com");
        // newCustomer.put("tell_no", "1234567890");
        // JSONObject insertResponse = postgrestClient.insert(newCustomer).exec();
        // System.out.println(insertResponse);        
    }
    
}
