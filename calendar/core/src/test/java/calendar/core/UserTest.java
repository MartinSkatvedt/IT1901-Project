package calendar.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class UserTest {

    private final User testUser = new User("Test");
    private final User testFriend = new User("Friend");

    @Test
    public void testConstructor() {
        assertEquals("Test", testUser.getUsername(), "Username was not set correctly.");
        assertFalse(testUser.getCalendar() == null, "Calendar was not set correctly");
    }

    @Test
    public void testAddFriend() {
        testUser.addFriend(testFriend);
        assertThrows(IllegalArgumentException.class, () -> testUser.addFriend(testFriend));
    }

    @Test
    public void testResetCalendar() {
        Calendar oldCalendar = testUser.getCalendar();
        testUser.resetCalendar();
        assertFalse(oldCalendar == testUser.getCalendar(), "Calendar was not reset");
    }
}
