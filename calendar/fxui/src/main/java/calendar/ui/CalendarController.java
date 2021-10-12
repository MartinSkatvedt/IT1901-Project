package calendar.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.event.ActionEvent;

import java.io.IOException;



public class CalendarController {

    @FXML private GridPane gp;

    @FXML private Button logOutButton;


    public void logOut(ActionEvent event) throws IOException {
        CalendarApp calendarApp = new CalendarApp();
        calendarApp.changeScene("Login.fxml");
    
        
    }

   
}
