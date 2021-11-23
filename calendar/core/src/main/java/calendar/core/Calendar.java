package calendar.core;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class that contains a HashMap of events with IDs
 */
public class Calendar {

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
        eventMap.put(event.getId(), event);
        return event;
    }

    /**
     * Method that deletes an event from a calendar by id
     * 
     * @param id id of event to be removed
     * @return event existed and was removed, or null
     */
    public Event deleteEvent(int id) {
        return eventMap.remove(id);
    }

    /**
     * Method that returns a list of all events in a calendar
     * 
     * @return list of events
     */
    public List<Event> getEvents() {
        return eventMap.values().stream().toList();
    }

    /**
     * Method that returns a list of events from a calendar based on a date.
     * 
     * @param date used to filter out events before the date
     * @return list of events after specified date
     */

    public List<Event> getEvents(LocalDate date) {
        List<Event> eventList = eventMap.values().stream().filter(e -> e.getDate().equals(date)).sorted((e1, e2) -> {
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
