package calendar.core;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class that contains a list of events
 */
public class Calendar {

    private List<Event> events = new ArrayList<Event>();
    private int idCounter;

    public Calendar() {
        this.idCounter = 1;
    }

    /**
     * Method that adds an event to a calendar
     * 
     * @param event event to be added to the calendar
     * @return added event
     */
    public Event addEvent(Event event) {
        event.setId(idCounter);
        idCounter++;
        events.add(event);
        return event;
    }

    /**
     * Method that removes an event from a calendar by index
     * 
     * @param i index of event to be removed
     * @return the removed event
     */
    public Event removeEvent(int i) {
        return (events.remove(i));
    }

    /**
     * Method that deletes an event from a calendar by id
     * 
     * @param id id of event to be removed
     * @return true if event existed and was removed
     */
    public boolean deleteEvent(int id) {
        return events.remove(getEvent(id));
    }

    /**
     * Method that returns a list of all events in a calendar
     * 
     * @return list of events
     */
    public List<Event> getEvents() {
        return events;
    }

    /**
     * Method that returns a list of events from a calendar based on a date.
     * 
     * @param date used to filter out events before the date
     * @return list of events after specified date
     */

    public List<Event> getEvents(LocalDate date) {
        List<Event> eventList = events.stream().filter(e -> e.getDate().equals(date)).sorted((e1, e2) -> {
            if (e1.getTimeHour() - e2.getTimeHour() == 0) {
                return e1.getTimeMinute().compareTo(e2.getTimeMinute());
            } else {
                return e1.getTimeHour().compareTo(e2.getTimeHour());
            }
        }).collect(Collectors.toList());
        return eventList;
    }

    /**
     * Method that returns event based on id.
     * 
     * @param id used to find event with given id
     * @return event object with given id, or null if the event was not found
     */

    public Event getEvent(int id) {
        for (Event e : events) {
            if (e.getId() == id) {
                return e;
            }
        }
        return null;
    }

    /**
     * Edit an event based on the attributes of a template event
     * 
     * @param id            id of event to edit
     * @param templateEvent template event
     */
    public void editEvent(int id, Event templateEvent) {
        if (getEvent(id) == null) {
            throw new IllegalArgumentException("No event with id \"" + id + "\"");
        }
        getEvent(id).setHeader(templateEvent.getHeader());
        getEvent(id).setDate(templateEvent.getDate());
        getEvent(id).setTime(templateEvent.getTimeString());
        getEvent(id).setDescription(templateEvent.getTimeString());
    }

}
