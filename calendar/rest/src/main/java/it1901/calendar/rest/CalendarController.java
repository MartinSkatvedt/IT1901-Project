package it1901.calendar.rest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import calendar.core.Calendar;
import calendar.core.Event;
import calendar.core.User;
import calendar.json.UserPersistence;

/**
 * Used for requests for a users calendar. PUT for handling editing of event.
 * POST for adding new event. DELETE for deleting event.
 */

@RestController
@RequestMapping(path = "/calendar", consumes = "application/json", produces = "application/json")
public class CalendarController {

    private Calendar calendar;
    private User user;
    private UserPersistence userPersistence;

    private static final Logger LOG = LoggerFactory.getLogger(CalendarController.class);

    /**
     * Initializes this CalendarController
     * 
     * @param user the user the calendar belongs to
     */

    // må initieres av usercontroller
    public CalendarController(User user) {
        this.user = user;
        this.calendar = user.getCalendar();
    }

    private void autoSaveUser() {
        if (userPersistence != null) {
            try {
                userPersistence.saveUser(this.user);
            } catch (IllegalStateException | IOException e) {
                System.err.println("Could not save user: " + e);
            }

        }
    }

    private void checkEvent(int id) {
        if (this.calendar.getEvent(id) == null) {
            throw new IllegalArgumentException("No event with id \"" + id + "\"");
        }
    }

    /**
     * Get all events in the calendar
     * 
     * @return a list of all events with HTTP response code 200 (OK)
     */

    // usikker på denne. Skal den returnere this.calendar eller events
    @GetMapping()
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> allEvents = this.calendar.getEvents();
        return ResponseEntity.ok(allEvents);
    }

    /**
     * Get a specific event based on id
     * 
     * @param id id of event
     * @return HTTP response code 200 (OK) with the event if it exists, else HTTP
     *         response code 404 (Not Found)
     */
    @GetMapping("/{id}")
    public ResponseEntity<Event> getEvent(@PathVariable int id) {
        try {
            checkEvent(id);
            Event event = this.calendar.getEvent(id);
            return ResponseEntity.ok(event);
        } catch (IllegalArgumentException e) {
            LOG.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Edit a specific event based on id
     * 
     * @param editedEvent the edited event
     * @param id          id of event to edit
     * @return HTTP response code 200 (OK) with the event if it exists and was
     *         edited, else HTTP response code 404 (Not Found)
     */
    @PutMapping("/{id}/edit")
    public ResponseEntity<Void> putEvent(@RequestBody Event editedEvent, @PathVariable int id) {
        try {
            checkEvent(id);
            calendar.editEvent(id, editedEvent);
            autoSaveUser();
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            LOG.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Add a new event to calendar
     * 
     * @param newEvent the event to add
     * @return HTTP response code 201 (Created), location header (URI) and the new
     *         event
     * @throws URISyntaxException
     */
    @PostMapping("/addEvent")
    public ResponseEntity<Event> postEvent(@RequestBody Event newEvent) throws URISyntaxException {
        Event event = this.calendar.addEvent(newEvent);
        autoSaveUser();
        return ResponseEntity.created(new URI("/" + event.getId())).body(event);
    }

    /**
     * Delete event from calendar
     * 
     * @param id id of event to delete
     * @return HTTP response code 200 (ok) if deleted, HTTP response code 404 (Not
     *         Found) if the event does not exist
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable int id) {
        try {
            checkEvent(id);
            this.calendar.deleteEvent(id);
            autoSaveUser();
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            LOG.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }

    }

}
