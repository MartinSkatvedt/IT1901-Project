package calendar.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CalendarTest {

    private calendar.core.Calendar testCalendar;

    @BeforeEach
    void newCalendar() {
        this.testCalendar = new calendar.core.Calendar();
    }

    @Test
    public void testConstructor() {
        assertTrue(testCalendar.getEvents().isEmpty());
    }

    @Test
    public void testAddEvent() {
        Event ev1 = new Event("header1", "desc1", LocalDate.now(), "11:00");
        Event ev2 = new Event("header2", "desc2", LocalDate.now(), "10:30");
        testCalendar.addEvent(ev1);
        testCalendar.addEvent(ev2);
        assertEquals(2, testCalendar.getEvents());
    }

    @Test
    public void testRemoveEvent() {
        Event e = new Event("header", "desc", LocalDate.now(), "10:00");
        testCalendar.addEvent(e);
        testCalendar.removeEvent(0);
        assertTrue(testCalendar.getEvents().isEmpty());
    }

    @Test
    public void testEventSort() {
        Event e1 = new Event("header", "desc", LocalDate.now(), "10:00");
        Event e2 = new Event("header1", "desc1", LocalDate.now(), "11:00");
        Event e3 = new Event("header2", "desc2", LocalDate.now(), "10:30");
        Event e4 = new Event("header3", "desc3", LocalDate.now(), "10:05");
        Event e5 = new Event("header4", "desc4", LocalDate.now(), "14:32");
        Event e6 = new Event("header5", "desc5", LocalDate.now(), "00:15");
        testCalendar.addEvent(e1);
        testCalendar.addEvent(e2);
        testCalendar.addEvent(e3);
        testCalendar.addEvent(e4);
        testCalendar.addEvent(e5);
        testCalendar.addEvent(e6);
        List<Event> sortedEvents = new ArrayList<>();
        sortedEvents.add(e6);
        sortedEvents.add(e1);
        sortedEvents.add(e4);
        sortedEvents.add(e3);
        sortedEvents.add(e2);
        sortedEvents.add(e5);
        for (int i = 0; i < 6; i++) {
            assertEquals(sortedEvents.get(i).getHeader(), testCalendar.getEvents(LocalDate.now()).get(i).getHeader(),
                    "Events were not sorted correctly.");
        }
    }

}
