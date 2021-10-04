package calendar.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class EventPlannerTest {

    private EventPlanner testPlanner = new EventPlanner();

    @Test
    public void testAddNewUser() {
        testPlanner.addNewUser("Test123");
        assertTrue(testPlanner.userNameTaken("Test123"), "Username should be taken");
        assertThrows(IllegalArgumentException.class, () -> testPlanner.addNewUser("Test123"));
        testPlanner.addNewUser("Test2");
        assertEquals(2, testPlanner.getAllUsers().size(), "Number of users is incorrect");
    }

    @Test
    public void testRemoveUser() {
        User test1 = new User("Friend");
        testPlanner.addNewUser(test1);
        testPlanner.removeUser("Friend");
        assertFalse(testPlanner.getAllUsers().contains(test1), "User was not removed correctly");
    }
}
