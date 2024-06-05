package page;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

public class Transactions {
    static float max_withdraw_amaount= 5000; 
    static float max_transfer_amaount=50000;
    static  boolean add_amount_flag, deduction_amount_flag;

    // sample variables
    int account_one=1, account_two=2;

    static void registerDepositTransaction(int account_no, float amount){
        String sql="insert into Transactions(sender_account, amount) values(?,?)";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            Alert alert;
            alert= new Alert(AlertType.ERROR);
            alert.setContentText("The Driver is not working, Try Again");
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.showAndWait();
            e.printStackTrace();
        }
        try (Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/BANK", "root","Root1234@@");
        PreparedStatement statement= con.prepareStatement(sql)){
            try{
                statement.setInt(1, account_no);
                statement.setFloat(2, amount);

                // executing query
                statement.executeUpdate();
                con.close();
            } catch (Exception e) {
                con.close();
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    static void registerTransferTransaction(int sender_account, int reciever_account, float amount){
        String sql="insert into Transactions(sender_account, receiver_account, amount) values(?,?,?)";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            Alert alert;
            alert= new Alert(AlertType.ERROR);
            alert.setContentText("The Driver is not working, Try Again");
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.showAndWait();
            e.printStackTrace();
        }
        try (Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/BANK", "root","Root1234@@");
        PreparedStatement statement= con.prepareStatement(sql)){
            try{
                statement.setInt(1, sender_account);
                statement.setInt(2, reciever_account);
                statement.setFloat(3, amount);

                // executing query
                statement.executeUpdate();
                con.close();
            } catch (Exception e) {
                con.close();
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    static void registerWithdrawTransaction(int account_no, float amount){
        amount*=-1;
        String sql="insert into Transactions(sender_account, amount) values(?,?)";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            Alert alert;
            alert= new Alert(AlertType.ERROR);
            alert.setContentText("The Driver is not working, Try Again");
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.showAndWait();
            e.printStackTrace();
        }
        try (Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/BANK", "root","Root1234@@");
        PreparedStatement statement= con.prepareStatement(sql)){
            try{
                statement.setInt(1, account_no);
                statement.setFloat(2, amount);

                // executing query
                statement.executeUpdate();
                con.close();
            } catch (Exception e) {
                con.close();
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    static void update(float amount, int sender_account, int reciever_account, boolean add_amount_flag,boolean deduction_amount_flag) throws SQLException{
        Alert alert;
        String sql="update AccountBalance set balance= balance - ? where account_no=?";

        try (Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/BANK", "root","Root1234@@");
        PreparedStatement statement= con.prepareStatement(sql)){
            try{
                statement.setFloat(1, amount);
                statement.setInt(2, sender_account);

                // executing query
                statement.executeUpdate();
                deduction_amount_flag=true;
                con.close();
            } catch (Exception e) {
                
                deduction_amount_flag=false;
                con.close();
                alert= new Alert(AlertType.ERROR);
                alert.setContentText("CoundN't excecute your query, Please Try again");
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.showAndWait();
            }
        }
        catch(SQLException ex){
            alert= new Alert(AlertType.ERROR);
            alert.setContentText("Couldnt connect to databse, Please Try again");
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.showAndWait();
            ex.printStackTrace();
        }

        sql="update AccountBalance set balance= balance + ? where account_no=?";

        try (Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/BANK", "root","Root1234@@");
        PreparedStatement statement= con.prepareStatement(sql)){
            try{
            statement.setFloat(1, amount);
            statement.setInt(2, reciever_account);
            
            statement.executeUpdate();
            add_amount_flag=true;
            con.close();
            } 
            catch (Exception e) {
                
                con.close();
                add_amount_flag=false;
                alert= new Alert(AlertType.ERROR);
                alert.setContentText("CoundN't excecute your query, Please Try again");
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.showAndWait();
            }
        }
        catch(SQLException ex){
            alert= new Alert(AlertType.ERROR);
            alert.setContentText("Couldnt connect to databse, Please Try again");
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.showAndWait();
            ex.printStackTrace();
        }
    }

    static void reverseUpdate(boolean add_amount_flag,boolean deduction_amount_flag, float amount, int sender_account, int reciever_account){
        Alert alert;
        String sql;
        if (add_amount_flag== false && deduction_amount_flag== true) {
            
            sql="update AccountBalance set balance= balance + ? where account_no=?";

            try (Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/BANK", "root","Root1234@@");
            PreparedStatement statement= con.prepareStatement(sql)){
                try{
                statement.setFloat(1, amount);
                statement.setInt(2, sender_account);
                
                statement.executeUpdate();
                deduction_amount_flag=true;
                con.close();
                } 
                catch (Exception e) {
                    
                    con.close();
                    deduction_amount_flag=false;
                    alert= new Alert(AlertType.ERROR);
                    alert.setContentText("CoundN't excecute your query, Please Try again");
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText(null);
                    alert.showAndWait();
                }
            }
            catch(SQLException ex){
                alert= new Alert(AlertType.ERROR);
                alert.setContentText("Couldnt connect to databse, Please Try again");
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.showAndWait();
                ex.printStackTrace();
            }
        }
        else if (add_amount_flag== true && deduction_amount_flag== false) {
            sql="update AccountBalance set balance= balance - ? where account_no=?";

            try (Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/BANK", "root","Root1234@@");
            PreparedStatement statement= con.prepareStatement(sql)){
                try{
                    statement.setFloat(1, amount);
                    statement.setInt(2, reciever_account);
    
                    // executing query
                    statement.executeUpdate();
                    add_amount_flag=true;
                    con.close();
                } catch (Exception e) {
                    // TODO: handle exception
                    add_amount_flag=false;
                    con.close();
                    alert= new Alert(AlertType.ERROR);
                    alert.setContentText("CoundN't excecute your query, Please Try again");
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText(null);
                    alert.showAndWait();
                }
            }
            catch(SQLException ex){
                alert= new Alert(AlertType.ERROR);
                alert.setContentText("Couldnt connect to databse, Please Try again");
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.showAndWait();
                ex.printStackTrace();
            }
        }
    }


    static float checkBalance(int account_no) throws Exception{
        String balance="";
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // TODO: handle exception
            System.out.println("Class not found");
        }

       String sql= "select balance from AccountBalance where account_no= ?";
        
        try(Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/BANK", "root","Root1234@@");
            PreparedStatement statement= con.prepareStatement(sql);) {
            statement.setInt(1, account_no);

           try( ResultSet rs= statement.executeQuery();){
                if (rs.next()) {
                   balance = rs.getString("balance");
                }
           }

            con.close();
        } catch (SQLException e) {
            // TODO: handle exception
            System.out.println("daatabse doesnt connect");
            e.printStackTrace();
        }
        
        float bal= Float.parseFloat(balance);
        return bal;
    }

    public static void deposit(String account_nun, String deposit_amount){
        Alert alert;
        float amount=0;
        float balance_before_deposit=0;
        float balance_after_deposit=0;
        int account_no= Integer.parseInt(account_nun);

        try {
            balance_before_deposit = Transactions.checkBalance(account_no);
        } catch (Exception e) {
            e.printStackTrace();
            Deposit.main(null);
        }

        if (InputValidator.isFloat( deposit_amount) || deposit_amount=="") {
            amount= Float.parseFloat(deposit_amount);
            {
                try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                    } catch (Exception e) {
                        // TODO: handle exception
                        alert= new Alert(AlertType.ERROR);
                        alert.setContentText("The Driver is not working");
                        alert.setTitle("Information Dialog");
                        alert.setHeaderText("null");
                        alert.showAndWait();
                        e.printStackTrace();
                    }
    
                    String sql="update AccountBalance set balance= balance + ? where account_no=?";
                    try(Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/BANK", "root","Root1234@@");
                    PreparedStatement statement= con.prepareStatement(sql);) {
                        statement.setFloat(1, amount);
                        statement.setInt(2, account_no);
    
                        try{
                            statement.executeUpdate();
                        }
                        catch(Exception ex){
                            alert= new Alert(AlertType.ERROR);
                            alert.setContentText("Error on statement execution, Try Again");
                            alert.setTitle("Information Dialog");
                            alert.setHeaderText("Error");
                            alert.showAndWait();
                            ex.printStackTrace();
                            con.close();
                        }
    
                            try{
                                balance_after_deposit= Transactions.checkBalance(account_no);
                            } catch (Exception e) {
                                
                                alert= new Alert(AlertType.ERROR);
                                alert.setContentText("Error on statement execution, Try Again");
                                alert.setTitle("Information Dialog");
                                alert.setHeaderText("null");
                                alert.showAndWait();
                                e.printStackTrace();
                                con.close();
                            }
    
                            // showinhg the befor and after balances
                            alert = new Alert(AlertType.INFORMATION);
                            alert.setContentText("balance before deposit =" + balance_before_deposit + "\n" +
                            "balance after deposit =" + balance_after_deposit);
                            alert.setTitle("Information Dialog");
                            alert.setHeaderText("Succesfully Deposited");
                            
                            Transactions.registerDepositTransaction(account_no, amount);
                            alert.getButtonTypes().setAll(ButtonType.OK);
                            Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
                            okButton.setOnAction(e -> {
                                // Perform your action here
                                System.out.println("OK button was clicked!");
                            });
                                    alert.showAndWait();
                             con.close();
    
                            //  add a conditin if the IK button of the alert is ckiked keading to the HOME page
                        } 
                        catch (Exception e) {
                            alert= new Alert(AlertType.ERROR);
                            alert.setContentText("the database is not connecting");
                            alert.setTitle("Information Dialog");
                            alert.setHeaderText("null");
                            alert.showAndWait();
                            e.printStackTrace();
                     }
                }
      

        }
  
    }

    static void withdraw(String account_num, String withdrawal_amoount){
        float amount=0,balance_before_withdraw =0;
        float balance_after_withdraw=0;
        int account_no=Integer.parseInt(account_num);
        Alert alert;
        String sql;
        
        try {
            balance_before_withdraw = Transactions.checkBalance(account_no);
        } catch (Exception e) {
            // error checking prevailing balance
            e.printStackTrace();
        }

        if (InputValidator.isFloat(withdrawal_amoount) || withdrawal_amoount=="") {
            amount=Float.parseFloat(withdrawal_amoount);
        }

        if (amount > balance_before_withdraw) {
            alert= new Alert(AlertType.ERROR);
            alert.setContentText("Insufficient balance, Try Again");
            alert.setTitle("Information Dialog");
            alert.setHeaderText("null");
            alert.showAndWait();
        }
        else if(amount > Transactions.max_withdraw_amaount){
            alert= new Alert(AlertType.ERROR);
            alert.setContentText("The amount is beyond the limit, Try Again");
            alert.setTitle("Information Dialog");
            alert.setHeaderText("null");
            alert.showAndWait();
        }
        else{
            try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                } catch (Exception e) {
                    // TODO: handle exception
                    alert= new Alert(AlertType.ERROR);
                    alert.setContentText("The Driver is not working, Try Again");
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText("null");
                    alert.showAndWait();
                    e.printStackTrace();
                }

                sql="update AccountBalance set balance= balance -? where account_no=?";
                try(Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/BANK", "root","Root1234@@");
                PreparedStatement statement= con.prepareStatement(sql);) {
                    statement.setFloat(1, amount);
                    statement.setInt(2, account_no);

                    try{
                        statement.executeUpdate();
                    }
                    catch(Exception ex){
                        alert= new Alert(AlertType.ERROR);
                        alert.setContentText("Error on statement execution, Try Again");
                        alert.setTitle("Information Dialog");
                        alert.setHeaderText("null");
                        alert.showAndWait();
                        ex.printStackTrace();
                        con.close();
                    }

                        try{
                            balance_after_withdraw= Transactions.checkBalance(account_no);
                        } catch (Exception e) {
                            // TODO: handle exception
                            alert= new Alert(AlertType.ERROR);
                            alert.setContentText("Error on statement execution, Try Again");
                            alert.setTitle("Information Dialog");
                            alert.setHeaderText("null");
                            alert.showAndWait();
                            e.printStackTrace();
                            con.close();
                        }

                        // showinhg the befor and after balances
                        alert = new Alert(AlertType.INFORMATION);
                        alert.setContentText("balance before withdraw =" + balance_before_withdraw + "\n" +
                        "balance after withr draw =" + balance_after_withdraw);
                        alert.setTitle("Information Dialog");
                        alert.setHeaderText("Succesfull withdrawal");

                        Transactions.registerWithdrawTransaction(account_no, amount);

                        alert.getButtonTypes().setAll(ButtonType.OK);
                        Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
                        okButton.setOnAction(e -> {
                            // Perform your action here
                            System.out.println("OK button was clicked!");
                        });
                                alert.showAndWait();
                         con.close();

                        //  add a conditin if the IK button of the alert is ckiked keading to the HOME page
                    } 
                    catch (Exception e) {
                        alert= new Alert(AlertType.ERROR);
                        alert.setContentText("the database is not connecting, Try Again");
                        alert.setTitle("Information Dialog");
                        alert.setHeaderText("null");
                        alert.showAndWait();
                        e.printStackTrace();
                 }
        }
    }

    static void transfer(String sender_account, String reciever_account, String transfer_amount){
       
        Alert alert;
        String sql= new String();
        Transactions.add_amount_flag=true;
        Transactions.deduction_amount_flag= true;
        int s_acount= Integer.parseInt(sender_account), r_account=0;
        float amount=0;
        float balance_before_transfer=0, balance_after_transfer=0;
        boolean check=false;

        try {
            balance_before_transfer= Transactions.checkBalance(s_acount);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        if (InputValidator.isFloat(transfer_amount)) {
            amount= Float.parseFloat(transfer_amount);
            if (InputValidator.isInteger(reciever_account)) {
                r_account= Integer.parseInt(reciever_account);

                if (amount> balance_before_transfer) {
                    alert= new Alert(AlertType.ERROR);
                    alert.setContentText("Insufficient balance");
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText(null);
                    alert.showAndWait();
                }
        
                else if(amount > Transactions.max_transfer_amaount){
                    alert= new Alert(AlertType.ERROR);
                    alert.setContentText("The amount is beyond the limit");
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText(null);
                    alert.showAndWait();
                }
        
                else{
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                    } catch (Exception e) {
        
                        alert= new Alert(AlertType.ERROR);
                        alert.setContentText("The Driver is not working, Try Again");
                        alert.setTitle("Information Dialog");
                        alert.setHeaderText(null);
                        alert.showAndWait();
                        e.printStackTrace();
                    }
        
        
                    sql= "SELECT (CASE WHEN (SELECT COUNT(balance) FROM AccountBalance WHERE account_no = ? ) = 1 THEN 'true' ELSE 'false' END) AS balance";
                    try (Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/BANK", "root","Root1234@@");
                    PreparedStatement statement= con.prepareStatement(sql)){
                        statement.setInt(1, r_account);
        
                        try (ResultSet rs= statement.executeQuery()){
                            rs.next();
                            String db_check=rs.getString("balance");
                             check= Boolean.parseBoolean(db_check);
                            con.close();
        
                            // check where the entered account esist in db or not
                            // if(check) System.out.println("true");
                            if (check) {
        
                                Transactions.update(amount,s_acount ,r_account, Transactions.add_amount_flag, Transactions.deduction_amount_flag);
                                // if(check) System.out.println("true");
                                // if(Transactions.add_amount_flag) System.out.println("true");
                                // if(Transactions.deduction_amount_flag) System.out.println("true");
                                if (Transactions.add_amount_flag==false || Transactions.deduction_amount_flag==false) {
                                    Transactions.reverseUpdate(Transactions.add_amount_flag, Transactions.deduction_amount_flag, amount, s_acount ,r_account);
                                    // check=false;
                                }   
                                else{
                                    balance_after_transfer= Transactions.checkBalance(s_acount);
                                    // if(check) System.out.println("true");
                                    // if(Transactions.add_amount_flag) System.out.println("true");
                                    // if(Transactions.deduction_amount_flag) System.out.println("true");
                                    alert= new Alert(AlertType.INFORMATION);
                                    alert.setContentText(
                                                        "   balance before transfer =" + balance_before_transfer + "\n" +
                                                        "   balance after transfer =" + balance_after_transfer);
                                    alert.setTitle("Information Dialog");
                                    alert.setHeaderText("Successfully Transfered");
                                    alert.showAndWait();
                                }
        
                            }
                            else{
                                alert= new Alert(AlertType.ERROR);
                                alert.setContentText("there is no account "+ r_account + "registered in the database");
                                alert.setTitle("Information Dialog");
                                alert.setHeaderText(null);
                                alert.showAndWait();
                            }
                        } catch (Exception e) {
                            
                            con.close();
                            alert= new Alert(AlertType.ERROR);
                            alert.setContentText("Error on fetching data from the database, Please Try Again");
                            alert.setTitle("Information Dialog");
                            alert.setHeaderText(null);
                            alert.showAndWait();
                        }
                        con.close();
                    } catch (Exception e) {
                        
                        alert= new Alert(AlertType.ERROR);
                        alert.setContentText("Couldn't connect to database, Please Try Again");
                        alert.setTitle("Information Dialog");
                        alert.setHeaderText(null);
                        alert.showAndWait();
                    }
                }

            } else {
                alert= new Alert(AlertType.ERROR);
                alert.setContentText("Error, Please Enter a valid number");
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Invalid Input");
                alert.showAndWait();
            }
        }
        else{
            alert= new Alert(AlertType.ERROR);
            alert.setContentText("Error, Please Enter a valid number");
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Invalid Input");
            alert.showAndWait();
        }

        if (check) {
            try {
                Transfertrial.registerTransferTransaction(s_acount, r_account, amount);
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    
    }


}

