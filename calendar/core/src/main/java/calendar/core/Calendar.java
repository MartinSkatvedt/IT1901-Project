package calendar.core;
import java.util.ArrayList;
import java.util.List; 

public class Calendar {

    private List<Event> events = new ArrayList<Event>(); 

    public Calendar(List<Event> events){
        this.events = events;

    }


    public void addEvent (Event event){
        events.add(event); 
    }

    public Event removeEvent(int i){
        return(events.remove(i)); 
    }
    
}
