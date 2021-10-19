package calendar.ui;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.time.temporal.WeekFields;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import calendar.core.Calendar;
import calendar.core.Event;
import calendar.core.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CalendarController {

    @FXML
    private VBox mon_1, tue_1, wed_1, thu_1, fri_1, sat_1, sun_1, mon_2, tue_2, wed_2, thu_2, fri_2, sat_2, sun_2,
            mon_3, tue_3, wed_3, thu_3, fri_3, sat_3, sun_3, mon_4, tue_4, wed_4, thu_4, fri_4, sat_4, sun_4, mon_5,
            tue_5, wed_5, thu_5, fri_5, sat_5, sun_5, mon_6, tue_6, wed_6, thu_6, fri_6, sat_6, sun_6;

    @FXML
    private List<VBox> dateCells;

    @FXML
    private Label monthLabel, week_1, week_2, week_3, week_4, week_5, week_6;

    @FXML
    private Button newEvent, prev_month, next_month;

    private LocalDate currentDate = LocalDate.now();

    private LocalDate displayedDate;

    private Calendar calendar;

    private User user;

    private void setCalendar(final Calendar calendar) {
        this.calendar = calendar;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @FXML
    void initialize() {

        setCalendar(this.user.getCalendar());
        this.displayedDate = this.currentDate;
        this.dateCells = Arrays.asList(mon_1, tue_1, wed_1, thu_1, fri_1, sat_1, sun_1, mon_2, tue_2, wed_2, thu_2,
                fri_2, sat_2, sun_2, mon_3, tue_3, wed_3, thu_3, fri_3, sat_3, sun_3, mon_4, tue_4, wed_4, thu_4, fri_4,
                sat_4, sun_4, mon_5, tue_5, wed_5, thu_5, fri_5, sat_5, sun_5, mon_6, tue_6, wed_6, thu_6, fri_6, sat_6,
                sun_6);
        updateCalendarView(this.currentDate);
    }

    @FXML
    private void onNewEvent() throws IOException {
        Stage stage = (Stage) newEvent.getScene().getWindow();
        stage.close();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("calendar/ui/Event.fxml"));
        EventController controller = new EventController();
        controller.setUser(this.user);
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void onClickedEvent(ActionEvent e) {
        String buttonText = ((Button) e.getSource()).getId();

        Event current = this.user.getCalendar().getEvents().stream()
                .filter((Event event) -> event.getHeader().equals(buttonText)).findFirst().orElse(null);

        Stage stage = (Stage) newEvent.getScene().getWindow();
        stage.close();
        FXMLLoader loader = new FXMLLoader(
                getClass().getClassLoader().getResource("calendar/ui/EventDescription.fxml"));
        EventDescriptionController controller = new EventDescriptionController();
        controller.setEvent(current);
        controller.setUser(this.user);

        loader.setController(controller);
        Parent root;
        try {
            root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private void updateCalendarView(LocalDate date) {
        this.displayedDate = date;
        // Finne måned og endre tittel til dette
        Month month = date.getMonth();
        this.monthLabel.setText(month.getDisplayName(TextStyle.FULL, Locale.ENGLISH) + " " + date.getYear());
        // Finne int verdien til ukedagen måneden starter på (mellom 1 og 7)
        int firstDayOfMonth = date.withDayOfMonth(1).getDayOfWeek().getValue();
        // Finne lengden på måneden
        int lengthOfMonth = month.length(date.isLeapYear());
        // Henter ut events for hver dato og setter header i riktig celle på kalender
        for (VBox cell : this.dateCells) {
            cell.getChildren().clear();
        }
        for (int i = 0; i < lengthOfMonth; i++) {

            Text text = new Text(" " + (i + 1));
            this.dateCells.get(firstDayOfMonth + i - 1).getChildren().add(text);

            for (Event e : calendar.getEvents(LocalDate.of(date.getYear(), month, i + 1))) {
                Button button = new Button();
                String buttonString = e.getTimeString() + " " + e.getHeader();
                button.setText(buttonString);
                button.setId(e.getHeader());
                button.setOnAction(ev -> onClickedEvent(ev));
                button.setFont(new Font("Calibri", 12));
                button.setPadding(new Insets(-2, 0, -2, 4));
                button.setStyle(
                        "-fx-background-color: white; -fx-border-color: #ff8700;  -fx-border-style: hidden hidden hidden solid; -fx-border-width: 2;");
                this.dateCells.get(firstDayOfMonth + i - 1).getChildren().add(button);
            }

        }
        if (month.equals(this.currentDate.getMonth())) {
            this.dateCells.get(firstDayOfMonth - 2 + date.getDayOfMonth())
                    .setStyle("-fx-border-width: 2; -fx-border-color: #ff8700;");
        }

        // Finne uketallene
        LocalDate monthStart = date.withDayOfMonth(1);
        int week1 = monthStart.get(WeekFields.ISO.weekOfWeekBasedYear());
        int week2 = monthStart.plusWeeks(1).get(WeekFields.ISO.weekOfWeekBasedYear());
        int week3 = monthStart.plusWeeks(2).get(WeekFields.ISO.weekOfWeekBasedYear());
        int week4 = monthStart.plusWeeks(3).get(WeekFields.ISO.weekOfWeekBasedYear());
        int week5 = monthStart.plusWeeks(4).get(WeekFields.ISO.weekOfWeekBasedYear());
        int week6 = monthStart.plusWeeks(5).get(WeekFields.ISO.weekOfWeekBasedYear());

        this.week_1.setText("" + week1);
        this.week_2.setText("" + week2);
        this.week_3.setText("" + week3);
        this.week_4.setText("" + week4);
        this.week_5.setText("" + week5);
        this.week_6.setText("" + week6);
    }

    @FXML
    private void onPrevMonth() {
        updateCalendarView(this.displayedDate.minusMonths(1));
    }

    @FXML
    private void onNextMonth() {
        updateCalendarView(this.displayedDate.plusMonths(1));
    }
}