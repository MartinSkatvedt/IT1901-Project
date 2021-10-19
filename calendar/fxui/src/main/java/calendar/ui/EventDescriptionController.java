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
import calendar.json.UserPersistence;

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
        eventDescriptionLabel.setEditable(false);

        this.eventHeadlineLabel.setText(this.currentEvent.getHeader());
        this.dateLabel.setText(this.currentEvent.getDate().toString());
        this.timeLabel.setText(this.currentEvent.getTimeString());
        this.eventDescriptionLabel.setText(this.currentEvent.getDescription());
    }

    @FXML
    void onEditEvent() throws IOException {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("calendar/ui/Event.fxml"));
        EventController controller = new EventController();
        controller.setUser(this.user);
        controller.setEvent(this.currentEvent);
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void onDeleteEvent() throws IOException {
        this.user.getCalendar().getEvents().remove(this.currentEvent);

        UserPersistence userPersistence = new UserPersistence();
        userPersistence.setSaveFile(user.getUsername() + ".json");
        userPersistence.saveUser(this.user);

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





