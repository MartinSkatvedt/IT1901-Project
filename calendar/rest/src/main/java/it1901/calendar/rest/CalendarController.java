package it1901.calendar.rest;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import calendar.core.Calendar;
import calendar.core.Event;
import calendar.core.RequestEvent;
import calendar.core.User;
import calendar.json.UserPersistence;

/**
 * Used for requests for a users calendar. PUT for replacing an event. POST for
 * adding a new event. DELETE for deleting an event.
 */

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class CalendarController {

    private User user;
    private UserPersistence userPersistence;

    private static final Logger LOG = LoggerFactory.getLogger(CalendarController.class);

    /**
     * Initializes this CalendarController
     * 
     * @param user the user the calendar belongs to
     */
    public CalendarController() {
        this.userPersistence = new UserPersistence();
    }

    private void autoSaveUser() {
        try {
            userPersistence.saveUser(this.user);
        } catch (IllegalStateException | IOException e) {
            LOG.error("Could not save user " + e);
        }
    }

    private void checkEvent(int id) {
        if (this.user.getCalendar().getEvent(id) == null) {
            throw new IllegalArgumentException("No event with id \"" + id + "\"");
        }
    }

    private void loadUser(String username) throws IllegalStateException, FileNotFoundException, IOException {
        LOG.info("Loading user: " + username);
        userPersistence.setSaveFile(username + ".json");
        User user = userPersistence.loadUser();
        if (user == null) {
            throw new IllegalArgumentException("No user with username: " + username);
        }
        this.user = user;
    }

    /**
     * Return calendar of user
     * 
     * @param username username of user
     * @return the calendar
     */
    @GetMapping("/calendar/{username}")
    public ResponseEntity<Calendar> getCalendar(@PathVariable String username) {
        LOG.info("getCalendar({})", username);
        try {
            loadUser(username);
        } catch (IOException e) {
            LOG.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        } catch (IllegalArgumentException e) {
            LOG.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(this.user.getCalendar());
    }

    /**
     * Get a specific event based on id
     * 
     * @param id id of event
     * @return the event
     */
    @GetMapping("/calendar/{username}/{id}")
    public ResponseEntity<Event> getEvent(@PathVariable String username, @PathVariable int id) {
        LOG.info("getEvent({}, {})", username, id);
        try {
            loadUser(username);
            checkEvent(id);
            Event event = this.user.getCalendar().getEvent(id);
            return ResponseEntity.ok(event);
        } catch (IllegalArgumentException e) {
            LOG.error(e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (IOException e) {
            LOG.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Replace event with specific id
     * 
     * @param event    edited event (class RequestEvent)
     * @param id       id of event to edit
     * @param username username
     * @return HTTP response 200 (OK) if event was replaced, 404 (Not Found) if
     *         event with id does not exist
     */
    @PutMapping("/calendar/{username}/{id}")
    public ResponseEntity<Void> replaceEvent(@RequestBody RequestEvent event, @PathVariable int id,
            @PathVariable String username) {
        LOG.info("replaceEvent({}, {})", id, username);
        try {
            loadUser(username);
            checkEvent(id);
            Event newEvent = new Event();
            newEvent.setHeader(event.getHeader());
            newEvent.setDescription(event.getDescription());
            newEvent.setId(id);
            newEvent.setTime(event.getTime());
            newEvent.setDate(event.getLocalDate());
            this.user.getCalendar().replaceEvent(newEvent);
            autoSaveUser();
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            LOG.error(e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (IOException e) {
            LOG.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Add a new event to calendar
     * 
     * @param event    the event to add (class RequestEvent)
     * @param username username
     * @return HTTP response code 201 (Created), location header (URI) and the new
     *         event
     * @throws URISyntaxException
     */
    @PostMapping("/calendar/{username}")
    public ResponseEntity<Event> postEvent(@RequestBody RequestEvent event, @PathVariable String username)
            throws URISyntaxException {
        LOG.info("postEvent({})", username);
        try {
            loadUser(username);
            Event newEvent = new Event();
            newEvent.setHeader(event.getHeader());
            newEvent.setDescription(event.getDescription());
            newEvent.setTime(event.getTime());
            newEvent.setDate(event.getLocalDate());
            this.user.getCalendar().addEvent(newEvent);
            autoSaveUser();
            return ResponseEntity.created(new URI("/calendar/" + username + "/" + newEvent.getId())).body(newEvent);
        } catch (IOException e) {
            LOG.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Delete event from calendar
     * 
     * @param id id of event to delete
     * @return HTTP response code 200 (ok) if deleted, HTTP response code 404 (Not
     *         Found) if the event does not exist
     */
    @DeleteMapping("/calendar/{username}/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable String username, @PathVariable int id) {
        LOG.info("deleteEvent({}, {})", username, id);
        try {
            loadUser(username);
            checkEvent(id);
            this.user.getCalendar().deleteEvent(id);
            autoSaveUser();
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            LOG.error(e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (IOException e) {
            LOG.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }

    }

}
