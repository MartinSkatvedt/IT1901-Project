package calendar.core;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserTest {
    private User testUser;


    @BeforeEach
    void newCalendar() {
        this.testUser = new User("test123");
    }

    @Test
    public void testConstructor() {
        assertEquals("test123", testUser.getUsername(), "Username was not set correctly.");
        assertFalse(testUser.getCalendar() == null, "Calendar was not set correctly");
    
        assertAll("Testing illegal names",
            () -> assertThrows(IllegalArgumentException.class, () -> new User("")),
            () -> assertThrows(IllegalArgumentException.class, () -> new User("@@@")),
            () -> assertThrows(IllegalArgumentException.class, () -> new User("!!!test@@@"))
        );    
    }

    @Test
    public void testResetCalendar() {
        Calendar oldCalendar = testUser.getCalendar();
        testUser.resetCalendar();
        assertFalse(oldCalendar == testUser.getCalendar(), "Calendar was not reset");
    }
}