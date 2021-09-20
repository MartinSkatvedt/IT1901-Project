package calendar.core;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class EventTest {
    private Event event;

    @BeforeEach
    void resetEvent() {
        this.event = new Event("testHeader", "testDesc", new GregorianCalendar());
    }

    @Test
    @DisplayName("Test for the setHeader method")
    void setHeaderTest() {
        assertEquals("testHeader", this.event.getHeader());
        this.event.setHeader("newHeader");
        assertEquals("newHeader", this.event.getHeader());
        assertThrows(IllegalArgumentException.class, () -> this.event.setHeader(""));
        assertThrows(IllegalArgumentException.class, () -> this.event.setHeader("   "));

    }

    @Test
    @DisplayName("Test for the setDescription method")
    void setDescTest() {
        assertEquals("testDesc", this.event.getDescription());
        this.event.setDescription("newDesc");
        assertEquals("newDesc", this.event.getDescription());
        assertThrows(IllegalArgumentException.class, () -> this.event.setDescription(""));
        assertThrows(IllegalArgumentException.class, () -> this.event.setDescription("  "));
    }

    @Test
    @DisplayName("Test for the setDate method")
    void setDateTest() {
        Calendar testDate1 = new GregorianCalendar(2021, 9, 20, 18, 30);
        this.event.setDate(testDate1);
        assertEquals(testDate1, this.event.getDate());
        Calendar testDate2 = new GregorianCalendar(2021, 10, 10, 10 ,10);
        this.event.setDate(testDate2);
        assertEquals(testDate2, this.event.getDate());
    }


}
