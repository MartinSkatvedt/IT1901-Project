package calendar.json;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import calendar.core.Event;
import calendar.core.User;

public class UserPersistenceTest {

    private UserPersistence userPersistence = new UserPersistence();

    @Test
    @DisplayName("Testing serializers and deserializers")
    public void persistenceTest() {
        
        userPersistence.setSaveFile("testJSON");
        User user = new User("testUserName");
        Event event1 = new Event("event1", "eventDesc1", LocalDate.of(2021, 10, 10), "12:00");
        user.getCalendar().addEvent(event1);

        try {
            userPersistence.saveUser(user);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
    
}
