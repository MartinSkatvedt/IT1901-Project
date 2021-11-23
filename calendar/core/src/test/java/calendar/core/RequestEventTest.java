package calendar.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RequestEventTest {

    private RequestEvent event = new RequestEvent();

    @Test
    @DisplayName("Test for the setHeader method")
    public void testHeader() {
        event.setHeader("Test");
        assertEquals("Test", event.getHeader(), "Header was not set correctly.");
    }

    @Test
    @DisplayName("Test for the setDescription method")
    public void testDescription() {
        event.setDescription("Description");
        assertEquals("Description", event.getDescription(), "Description was not set correctly");
    }

    @Test
    @DisplayName("Test for the setId method")
    public void testId() {
        event.setId(1);
        assertEquals(1, event.getId(), "Id was not set correctly");
    }

    @Test
    @DisplayName("Test for the setDate method")
    public void testDate() {
        event.setDate("2021-12-24");
        assertEquals("2021-12-24", event.getDateString(), "Date was not set correctly");
        assertEquals("2021-12-24", event.getLocalDate().toString(), "Date was not converted to LocalDate correctly");
        assertThrows(IllegalArgumentException.class, () -> event.setDate("hallo"));
    }

    @Test
    @DisplayName("Test for the setTime method")
    public void testTime() {
        event.setTime("12:00");
        assertEquals("12:00", event.getTime(), "Time was not set correctly");
        assertThrows(IllegalArgumentException.class, () -> event.setTime(""));
        assertThrows(IllegalArgumentException.class, () -> event.setTime("5"));
        assertThrows(IllegalArgumentException.class, () -> event.setTime("99:01"));
    }
}
