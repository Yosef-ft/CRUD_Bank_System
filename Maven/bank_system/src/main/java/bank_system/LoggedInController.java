package bank_system;

import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;

public class LoggedInController implements Initializable {


    @FXML
    private Button accType_button;

    @FXML
    private Button balance_button;

    @FXML
    private Button deposite_button;

    @FXML
    private Button exchange_button;

    @FXML
    private Button home_button;

    @FXML
    private Button home_button2;

    @FXML
    private AnchorPane home_pane;

    @FXML
    private AnchorPane home_pane2;

    @FXML
    private Button live_button;

    @FXML
    private Button live_button2;

    @FXML
    private Button logout_button;

    @FXML
    private Label menuBack_button;

    @FXML
    private Label menu_button;

    @FXML
    private Button recent_button;

    @FXML
    private AnchorPane slider;

    @FXML
    private Button topUp_button;

    @FXML
    private Button transactLocal_button;

    @FXML
    private Button transfer_button;

    @FXML
    private ImageView travel_button;

    @FXML
    private Button user_button;

    @FXML
    private Button utilities_button;

    @FXML
    private Button withdraw_button;

    public static String Email = Utils.Email;
    String account_no = Customer_server.Account_no_retriver(Email);

    @Override
    public void initialize(URL location, ResourceBundle resources){

        slider.setTranslateX(-176);

        menu_button.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToX(0);
            slide.play();
            home_pane.setVisible(false);

            slider.setTranslateX(-176);

            slide.setOnFinished((ActionEvent e)->{
                menuBack_button.setVisible(true);
                menu_button.setVisible(false);
            });
        });


        menuBack_button.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToX(-176);
            slide.play();

            slider.setTranslateX(0);

            slide.setOnFinished((ActionEvent e)->{
                menuBack_button.setVisible(false);
                menu_button.setVisible(true);
                home_pane.setVisible(true);
            });
        });    
        
        live_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                // String checkout_url = ChapaConnection.payment("Yosef@gmail.com", "yosef", "fetene", "100").getAsJsonObject("detail").getAsJsonObject("data").get("checkout_url").getAsString();
                // ChapaConnection.checkout_url = checkout_url;
                Utils.ChangeScene(event, "Chapa live payment", "linkOpener.fxml", Email);
            }
        });

        live_button2.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){

                Utils.ChangeScene(event, "Chapa live payment", "linkOpener.fxml", Email);                
            }
        });

        deposite_button.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                Parent root;
                try {
                    root = FXMLLoader.load(getClass().getResource("Deposit.fxml"));
                    Scene scene = new Scene(root);
    
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        withdraw_button.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                Parent root;
                try {
                    root = FXMLLoader.load(getClass().getResource("Withdraw.fxml"));
                    Scene scene = new Scene(root);
    
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });  
        
        balance_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                
                try {
                    float balance = Transaction_server.checkBalance(Integer.valueOf(account_no));
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Your balance is " + balance);
                    alert.setHeaderText("Balance");
                    alert.show();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });

        transfer_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                Parent root;
                try{
                    root = FXMLLoader.load(getClass().getResource("Transfer.fxml"));
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.setTitle("Transfer");
                    stage.show();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        });
    }

}
