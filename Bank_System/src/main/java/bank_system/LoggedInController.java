package bank_system;

import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.net.URL;

public class LoggedInController implements Initializable {

    @FXML
    private Label menuBack_button;

    @FXML
    private Label menu_button;

    @FXML
    private AnchorPane slider;

    @Override
    public void initialize(URL location, ResourceBundle resources){

        slider.setTranslateX(-176);

        menu_button.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToX(0);
            slide.play();

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
            });
        });        
    }

}
