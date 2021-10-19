package calendar.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;

public class UserTest {

    private final User testUser = new User("Test");

    @Test
    public void testConstructor() {
        assertEquals("Test", testUser.getUsername(), "Username was not set correctly.");
        assertFalse(testUser.getCalendar() == null, "Calendar was not set correctly");
    }


    @Test
    public void testResetCalendar() {
        Calendar oldCalendar = testUser.getCalendar();
        testUser.resetCalendar();
        assertFalse(oldCalendar == testUser.getCalendar(), "Calendar was not reset");
    }
}
