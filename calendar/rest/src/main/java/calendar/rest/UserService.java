package calendar.rest;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import calendar.core.Calendar;
import calendar.core.Event;
import calendar.core.RequestEvent;
import calendar.core.User;
import calendar.json.UserPersistence;

/**
 * REST-service class that handles requests from REST-controllers
 */

@Service
public class UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private User user;

    private Calendar calendar;

    private UserPersistence userPersistence = new UserPersistence();

    /**
     * Loads user from existing save file, or creates a new user. Used for
     * GET-requests at endpoint "/user/{username}"
     * 
     * @param username username of user
     * @return the loaded or created user
     * @throws IllegalStateException
     * @throws FileNotFoundException
     * @throws IOException
     */
    public User getUser(String username) throws IllegalStateException, FileNotFoundException, IOException {
        userPersistence.setSaveFile(username + ".json");
        if (userPersistence.loadUser() == null) {
            this.user = new User(username);
        } else {
            this.user = userPersistence.loadUser();
        }
        autoSaveUser();
        return this.user;
    }

    /**
     * Gets calendar from user. Used for GET requests at endpoint
     * "/calendar/{username}"
     * 
     * @param username username of user to get calendar from
     * @return the users calendar
     * @throws IllegalStateException
     * @throws FileNotFoundException
     * @throws IOException
     */
    public Calendar getCalendar(String username) throws IllegalStateException, FileNotFoundException, IOException {
        loadUser(username);
        return this.calendar;
    }

    /**
     * Gets event with specific id. Used for GET requests at endpoint
     * "/calendar/{username}/{id}"
     * 
     * @param username username of user to get event from
     * @param id       id of event
     * @return event with given id
     * @throws IllegalStateException
     * @throws FileNotFoundException
     * @throws IOException
     */
    public Event getEvent(String username, int id) throws IllegalStateException, FileNotFoundException, IOException {
        loadUser(username);
        checkEvent(id);
        return calendar.getEvent(id);
    }

    /**
     * Replaces event with specific id with a new event. Used for PUT requests at
     * endpoint "/calendar/{username}/{id}"
     * 
     * @param event    new event to replace old event
     * @param username username of user
     * @param id       id of event to replace
     * @return
     * @throws IllegalStateException
     * @throws FileNotFoundException
     * @throws IOException
     */
    public Event replaceEvent(RequestEvent event, String username, int id)
            throws IllegalStateException, FileNotFoundException, IOException {
        loadUser(username);
        checkEvent(id);
        Event newEvent = new Event();
        newEvent.setHeader(event.getHeader());
        newEvent.setDescription(event.getDescription());
        newEvent.setId(id);
        newEvent.setTime(event.getTime());
        newEvent.setDate(event.getLocalDate());
        calendar.replaceEvent(newEvent);
        autoSaveUser();
        return newEvent;

    }

    /**
     * Posts a new event to the users calendar. Used for POST requests at endpoint
     * "/calendar/{username}"
     * 
     * @param event    new event to add
     * @param username username of user
     * @return added event
     * @throws IllegalStateException
     * @throws FileNotFoundException
     * @throws IOException
     */
    public Event postEvent(RequestEvent event, String username)
            throws IllegalStateException, FileNotFoundException, IOException {
        loadUser(username);
        Event newEvent = new Event();
        newEvent.setHeader(event.getHeader());
        newEvent.setDescription(event.getDescription());
        newEvent.setTime(event.getTime());
        newEvent.setDate(event.getLocalDate());
        calendar.addEvent(newEvent);
        autoSaveUser();
        return newEvent;
    }

    /**
     * Deletes event with given id from the users calendar. Used for DELETE requests
     * at endpoint "/calendar/{username}/{id}"
     * 
     * @param username username of user
     * @param id       id of event to delete
     * @return
     * @throws IllegalStateException
     * @throws FileNotFoundException
     * @throws IOException
     */
    public Event deleteEvent(String username, int id) throws IllegalStateException, FileNotFoundException, IOException {
        loadUser(username);
        checkEvent(id);
        Event event = calendar.deleteEvent(id);
        autoSaveUser();
        return event;
    }

    private void autoSaveUser() {
        try {
            userPersistence.setSaveFile(user.getUsername() + ".json");
            userPersistence.saveUser(user);
        } catch (IllegalStateException | IOException e) {
            LOG.error("Could not save user " + e);
        }
    }

    private void loadUser(String username) throws IllegalStateException, FileNotFoundException, IOException {
        userPersistence.setSaveFile(username + ".json");
        if (userPersistence.loadUser() == null) {
            throw new IllegalArgumentException("No user with username " + username);
        }
        this.user = userPersistence.loadUser();
        this.calendar = user.getCalendar();
    }

    private void checkEvent(int id) {
        if (calendar.getEvent(id) == null) {
            throw new IllegalArgumentException("No event with id " + id);
        }
    }

}
