package calendar.ui;

import java.io.IOException;
import java.time.LocalDate;

import calendar.core.Event;
import calendar.core.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EventController {

    @FXML
    private TextField title, time;
    @FXML
    private DatePicker date;
    @FXML
    private TextArea description;
    @FXML
    private Button addEvent;

    private User user;

    @FXML
    void initialize() {

    }

    @FXML
    private void onAddEvent() throws IOException {
        if (this.title.getText().isBlank()) {
            this.description.setText("Event must have title");
            return;
        }
        String title = this.title.getText();
        LocalDate date = this.date.getValue();
        // String time = this.time.getText();
        String description = this.description.getText();
        Event event = new Event(title, description, date);
        this.user.getCalendar().addEvent(event);

        Stage stage = (Stage) addEvent.getScene().getWindow();
        stage.close();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("calendar/ui/Calendar.fxml"));
        CalendarController controller = new CalendarController();
        controller.setUser(this.user);
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void setUser(User user) {
        this.user = user;
    }
}
