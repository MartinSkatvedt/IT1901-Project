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
    private List<Event> testEvents = new ArrayList<Event>(); 

    @BeforeEach
    void newCalendar() {
        this.testCalendar = new calendar.core.Calendar(); 
        this.testEvents = testCalendar.getEvents(); 
    }
    @Test
    public void testConstructor(){
        assertTrue(testEvents.isEmpty());
    }
  
    @Test
    public void testAddEvent(){
        Event ev1 = new Event("header1", "desc1", LocalDate.now()); 
        Event ev2 = new Event("header2", "desc2", LocalDate.now()); 
        testCalendar.addEvent(ev1);
        testCalendar.addEvent(ev2);
        assertEquals(2, testEvents.size());
    }
    @Test
    public void testRemoveEvent(){
        Event e = new Event("header", "desc", LocalDate.now()); 
        testEvents.add(e); 
        testCalendar.removeEvent(0); 
        assertTrue(testEvents.isEmpty());
    }
    
}

    

