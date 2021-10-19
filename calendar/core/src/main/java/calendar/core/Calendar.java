package calendar.core;

import java.time.LocalDate;
import java.util.ArrayList;
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
