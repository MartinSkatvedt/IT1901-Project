package calendar.core;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class that contains a list of events
 */
public class Calendar {

    private List<Event> events = new ArrayList<Event>();
    private Map<Integer, Event> eventMap = new HashMap<>();
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
        if (event.getId() == 0) {
            event.setId(idCounter);
            idCounter++;
        }
        events.add(event);
        eventMap.put(event.getId(), event);
        return event;
    }

    /**
     * Method that removes an event from a calendar by index
     * 
     * @param i index of event to be removed
     * @return the removed event
     */
    public Event removeEvent(int i) {
        eventMap.remove(events.get(i).getId());
        return (events.remove(i));
    }

    /**
     * Method that deletes an event from a calendar by id
     * 
     * @param id id of event to be removed
     * @return true if event existed and was removed
     */
    public boolean deleteEvent(int id) {
        eventMap.remove(id);
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
        return eventMap.get(id);
    }

    /**
     * Edit an event based on the attributes of a template event
     * 
     * @param id            id of event to edit
     * @param templateEvent template event
     */
    public void replaceEvent(Event newEvent) {
        if (getEvent(newEvent.getId()) == null) {
            throw new IllegalArgumentException("No event with id \"" + newEvent.getId() + "\"");
        }
        eventMap.put(newEvent.getId(), newEvent);
    }

}
