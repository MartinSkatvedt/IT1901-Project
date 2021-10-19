package calendar.core;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class EventTest {
    private Event event;

    @BeforeEach
    void resetEvent() {
        this.event = new Event("testHeader", "testDesc", LocalDate.now(), "12:00");
    }

    @Test
    @DisplayName("Test for the class constructor")
    void testConstructor() {
        LocalDate date1 = LocalDate.now();
        Event event1 = new Event("abc", "def", date1, "13:30");
        assertAll(() -> assertEquals("abc", event1.getHeader()), () -> assertEquals("def", event1.getDescription()),
                () -> assertEquals(date1, event1.getDate()), () -> assertEquals("13:30", event1.getTimeString()));
        assertThrows(IllegalArgumentException.class, () -> new Event("", "abc", date1, "12:00"));
        assertThrows(IllegalArgumentException.class, () -> new Event("def", "abc", date1, ""));
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
    }

    @Test
    @DisplayName("Test for the setDate method")
    void setDateTest() {
        LocalDate testDate1 = LocalDate.now();
        this.event.setDate(testDate1);
        assertEquals(testDate1, this.event.getDate());
        LocalDate testDate2 = LocalDate.of(1999, 12, 12);
        this.event.setDate(testDate2);
        assertEquals(testDate2, this.event.getDate());
    }

    @Test
    @DisplayName("Test for the setTime method")
    void setTimeTest() {
        this.event.setTime("11:20");
        assertEquals("11:20", this.event.getTimeString());
        this.event.setTime("23:45");
        assertEquals("23:45", this.event.getTimeString());
        assertThrows(IllegalArgumentException.class, () -> this.event.setTime(""));
    }
}
