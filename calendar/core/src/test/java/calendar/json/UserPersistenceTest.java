package calendar.json;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import calendar.core.Event;
import calendar.core.User;

public class UserPersistenceTest {

    private UserPersistence userPersistence = new UserPersistence();

    @Test
    @DisplayName("Testing serializers and deserializers")
    public void persistenceTest() {
        
        userPersistence.setSaveFile("testJSON.json");
        User user = new User("testUserName");
        Event event1 = new Event("event1", "eventDesc1", LocalDate.of(2021, 10, 10), "12:00");
        user.getCalendar().addEvent(event1);
        Event event2 = new Event("event2", "eventDesc2", LocalDate.of(2021, 1, 1), "12:00");
        user.getCalendar().addEvent(event2);

        try {
            userPersistence.saveUser(user);
            User loadedUser = userPersistence.loadUser();
            assertEquals(user.getUsername(), loadedUser.getUsername());
            assertEquals(user.getCalendar().getEvents().size(), loadedUser.getCalendar().getEvents().size());
            assertEquals(user.getCalendar().getEvents().get(0).getHeader(), loadedUser.getCalendar().getEvents().get(0).getHeader());
            assertEquals(user.getCalendar().getEvents().get(1).getHeader(), loadedUser.getCalendar().getEvents().get(1).getHeader());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }   
}
