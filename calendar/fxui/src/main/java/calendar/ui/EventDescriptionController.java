package calendar.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import java.io.IOException;
import calendar.core.Event;
import calendar.core.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class EventDescriptionController {

    private User user;
    private Event currentEvent;

    @FXML private Label eventHeadlineLabel;
    @FXML private Label dateLabel;
    @FXML private Label timeLabel;
    @FXML private TextArea eventDescriptionLabel;
    @FXML private Button backButton;
    @FXML private Button editButton;
    @FXML private Button deleteButton;


    @FXML
    void initialize() {
        this.eventHeadlineLabel.setText(this.currentEvent.getHeader());
        this.dateLabel.setText(this.currentEvent.getDate().toString());
        this.eventDescriptionLabel.setText(this.currentEvent.getDescription());
    }

    @FXML
    void onEditEvent() {

    }

    @FXML
    void onDeleteEvent() throws IOException {
        this.user.getCalendar().getEvents().remove(this.currentEvent);
        onGoBack();
    }

    @FXML
    void onGoBack() throws IOException {
        this.switchScene("calendar/ui/Calendar.fxml");
    }

    private void switchScene(String path) throws IOException {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(path));
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

    public void setEvent(Event event) {
        this.currentEvent = event;
    }
}





