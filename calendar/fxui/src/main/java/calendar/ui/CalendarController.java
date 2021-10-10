package calendar.ui;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import calendar.core.Calendar;
import calendar.core.Event;
import calendar.core.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class CalendarController {

    @FXML
    private TextArea mon_1, tue_1, wed_1, thu_1, fri_1, sat_1, sun_1, mon_2, tue_2, wed_2, thu_2, fri_2, sat_2, sun_2,
            mon_3, tue_3, wed_3, thu_3, fri_3, sat_3, sun_3, mon_4, tue_4, wed_4, thu_4, fri_4, sat_4, sun_4, mon_5,
            tue_5, wed_5, thu_5, fri_5, sat_5, sun_5;

    @FXML
    private List<TextArea> dateCells = Arrays.asList(mon_1, tue_1, wed_1, thu_1, fri_1, sat_1, sun_1, mon_2, tue_2,
            wed_2, thu_2, fri_2, sat_2, sun_2, mon_3, tue_3, wed_3, thu_3, fri_3, sat_3, sun_3, mon_4, tue_4, wed_4,
            thu_4, fri_4, sat_4, sun_4, mon_5, tue_5, wed_5, thu_5, fri_5, sat_5, sun_5);

    @FXML
    private Label month;

    @FXML
    private Button newEvent;

    private LocalDate currentDate;

    private Calendar calendar;

    private User user;

    private void setCalendar(final Calendar calendar) {
        this.calendar = calendar;
    }

    @FXML
    void initialize() {
        this.user = new User(""); // fra fil
        setCalendar(this.user.getCalendar());
        this.currentDate = LocalDate.now();
        updateCalendarView(currentDate);
    }

    @FXML
    private void onNewEvent() {
        // åpne event
    }

    private void updateCalendarView(LocalDate date) { // mye feil her
        // Finne måned og endre tittel til dette
        Month month = date.getMonth();
        this.month.setText(month.getDisplayName(TextStyle.FULL, Locale.ENGLISH));
        // Finne dagen i året måneden starter på (int verdi)
        int firstDayOfYear = month.firstDayOfYear(date.isLeapYear());
        // Finne int verdien til ukedagen måneden starter på (mellom 1 og 7)
        int firstDayOfMonth = LocalDate.ofYearDay(date.getYear(), firstDayOfYear).getDayOfWeek().getValue();
        // Finne lengden på måneden
        int lengthOfMonth = month.length(date.isLeapYear());
        int dateNumber = 1;
        for (int i = firstDayOfMonth - 1; i < lengthOfMonth; i++) {
            String dateString = "";
            for (Event e : calendar.getEvents().stream()
                    .filter(e -> e.getDate().equals(LocalDate.of(date.getYear(), month, dateNumber)))
                    .collect(Collectors.toList())) {
                dateString += "\n" + e.getHeader();
            }
            this.dateCells.get(i).setText(dateNumber + "." + dateString);
        }
    }
}