package calendar.ui;

import java.time.LocalDate;

import calendar.core.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class EventController {

    @FXML private TextField title, time; 
    @FXML private DatePicker date;  
    @FXML private TextArea description; 
    @FXML private Button addEvent; 

    @FXML
    void initialize() {
        
    }   

    @FXML
    private void onAddEvent() {
        String title = this.title.getText(); 
        LocalDate date = this.date.getValue(); 
       // String time = this.time.getText(); 
        String description = this.description.getText(); 
        Event event = new Event(title, description, date); 
        System.out.println(event.getHeader());


    }
}
