package bank_system;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.Action;

public class AdminController implements Initializable{

    @FXML
    private PieChart aType_pieChart;

    @FXML
    private PieChart gender_pieChart;

    @FXML
    private Button home_button;

    @FXML
    private LineChart<String, Number> transactions_lineChart;

    ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList(
        new PieChart.Data("Male",   Customer_server.gender_counter().get(0)),
        new PieChart.Data("Female", Customer_server.gender_counter().get(1))
    );

    ObservableList<PieChart.Data> pieData2 = FXCollections.observableArrayList(
        new PieChart.Data("Checking account",Customer_server.AccType_counter().get(0)),
        new PieChart.Data("Business account", Customer_server.AccType_counter().get(2)),
        new PieChart.Data("Savings account", Customer_server.AccType_counter().get(1))
    );
    

    @Override
    public void initialize(URL location, ResourceBundle resources){
        gender_pieChart.setData(pieData);
        aType_pieChart.setData(pieData2);

        
        ((CategoryAxis) transactions_lineChart.getXAxis()).setLabel("TimeStamp");
        ((NumberAxis) transactions_lineChart.getYAxis()).setLabel("Total Capital");

        transactions_lineChart.setTitle("Total transactions");

        // create the data set that is going to be added to the line chart
        XYChart.Series data = new XYChart.Series();
        data.setName("Transactions");
        ArrayList<Integer> tList = new ArrayList<>();
        tList = Transaction_server.Transaction_counter();
        int total = 0;
        for (int i = 0; i< tList.size(); i++){
            total += tList.get(i);
            String xValue = String.valueOf(i);
            data.getData().add(new XYChart.Data(xValue,total));
        }

        transactions_lineChart.getData().add(data);

        home_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                Utils.ChangeScene(event, "Walia Bank", "MainWindow.fxml", null);
            }
        });
    }

}
