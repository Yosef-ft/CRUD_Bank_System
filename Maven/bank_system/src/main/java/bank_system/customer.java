package bank_system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

public class customer {

    static String connectionUrl =
    "jdbc:sqlserver://DESKTOP-1G41PS0\\SQLEXPRESS;"
                    + "database=Bank;"
                    + "integratedSecurity=true;"
                    + "encrypt=true;"
                    + "trustServerCertificate=true;"
                    + "loginTimeout=30;";


    public static void SignIn(ActionEvent event, String fName, String lName,String age, String email, String password, String gender, String tellNo, String account_type){

        PreparedStatement pCheckUser = null;
        PreparedStatement pInsert = null;
        PreparedStatement tInsert = null;
        ResultSet resultSet = null;

        
        try (Connection connection = DriverManager.getConnection(connectionUrl)){
            pCheckUser = connection.prepareStatement("select * from Customer where email = ?");
            pCheckUser.setString(1, email);
            resultSet = pCheckUser.executeQuery();

            if (resultSet.isBeforeFirst()){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("The email already exists please insert a valid email");
                alert.show();
            }else{
                pInsert = connection.prepareStatement("Insert into Customer values (?,?,?,?,?,?,?,?,?)");
                pInsert.setString(1, fName);
                pInsert.setString(2, lName);
                int int_age = Integer.valueOf(age);
                pInsert.setInt(3, int_age);
                pInsert.setString(4, gender);
                pInsert.setString(5, email);
                int phone_no = Integer.valueOf(tellNo);
                pInsert.setInt(6, phone_no);
                pInsert.setString(7, password);
                int account_no = 123474345; // Don't forget to make this random and unique so the database will accept it
                pInsert.setInt(8, account_no);
                pInsert.setString(9,account_type);

                tInsert = connection.prepareStatement("Insert into AccountBalance values (?, ?)");
                tInsert.setInt(1, account_no);
                tInsert.setInt(2, 0);

                pInsert.executeUpdate();
                tInsert.execute();
                

                Utils.ChangeScene(event, "Created Account", "LoggedIn.fxml", email);
            }

        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            if (resultSet != null){
                try{
                    resultSet.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
            if (pCheckUser != null){
                try{
                    pCheckUser.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
            if (pInsert != null){
                try{
                    pInsert.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
        }

    }

    public static void LogIn(ActionEvent event, String email, String password){
        
        PreparedStatement pCheckPass = null;
        ResultSet resultSet = null;

        try (Connection connection = DriverManager.getConnection(connectionUrl)){
            pCheckPass = connection.prepareStatement("Select password from Customer where email = ?");
            pCheckPass.setString(1, email);
            resultSet = pCheckPass.executeQuery();

            if(!resultSet.isBeforeFirst()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Invalid credentials please try again");
                alert.show();
            }else{
                while(resultSet.next()){
                    String retrived_password = resultSet.getString("password");
                    if(retrived_password.equals(password)){
                        Utils.ChangeScene(event, "Logged in" ,"LoggedIn.fxml", email);
                    }else{
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Incorrect password. Please try again.");
                        alert.show();
                    }
                }
            }

        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            if(pCheckPass != null){
                try{
                    pCheckPass.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
            if (resultSet != null){
                try{
                    resultSet.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static String Account_no_retriver(String email){

        PreparedStatement pStatement;
        ResultSet resultSet;
        
        String Account_no = null; // change the account number to string; makes it easier for transaction class

        try (Connection connection = DriverManager.getConnection(connectionUrl)){
            pStatement = connection.prepareStatement("Select account_no from customer where email = ?");
            pStatement.setString(1, email);
            resultSet = pStatement.executeQuery();

            if (!resultSet.isBeforeFirst()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Invalid Email address please try again");
                alert.show();
            }else{
                int account_no; 
                while(resultSet.next()){
                    account_no = resultSet.getInt("account_no");
                    Account_no = String.valueOf(account_no);
                }
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

        return Account_no;
    }
}
