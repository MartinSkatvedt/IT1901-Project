package calendar.core;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Calendar {

    private List<Event> events = new ArrayList<Event>();

    public Calendar() {

    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public Event removeEvent(int i) {
        return (events.remove(i));
    }

    public List<Event> getEvents() {
        return events;
    }

    public List<Event> getEvents(LocalDate date) {
        List<Event> eventList = events.stream().filter(e -> e.getDate().equals(date)).collect(Collectors.toList());
        Collections.sort(eventList);
        return eventList;
    }

}
