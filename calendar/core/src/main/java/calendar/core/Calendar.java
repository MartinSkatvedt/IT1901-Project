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

    public Calendar() {

    }

    /**
    * Method that adds an event to a calendar 
    * @param event event to be added to the calendar
    * @return void
    */
    public void addEvent(Event event) {
        events.add(event);
    }

    /**
    * Method that removes an event from a calendar by index
    * @param i index of event to be removed
    * @return the removed event 
    */
    public Event removeEvent(int i) {
        return (events.remove(i));
    }

    /**
    * Method that returns a list of all events in a calendar
    * @return list of events 
    */
    public List<Event> getEvents() {
        return events;
    }

    /**
    * Method that returns a list of events from a calendar based on a date.
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

}
