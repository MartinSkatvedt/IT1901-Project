package calendar.core;

import java.util.Calendar;

public class Event {
    private String header;
    private String description;
    private Calendar date;

    public Event(String headerArg, String descArg, Calendar dateArg) {
        this.setHeader(headerArg);
        this.setDescription(descArg);
        this.setDate(dateArg);
    }

    public void setHeader(String newHeader) {
        if (newHeader.replaceAll("\\s", "").isEmpty()) throw new IllegalArgumentException("New Header cannot be empty");
        this.header = newHeader;
    }

    public void setDescription(String newDesc) {
        if (newDesc.replaceAll("\\s", "").isEmpty()) throw new IllegalArgumentException("New Description cannot be empty");
        this.description = newDesc;
    }

    public void setDate(Calendar newDate) {
        this.date = (Calendar) newDate.clone();
    }

    public String getHeader() {
        return this.header;
    }

    public String getDescription() {
        return this.description;
    }

    public Calendar getDate() {
        return (Calendar) this.date.clone();
    }
}


