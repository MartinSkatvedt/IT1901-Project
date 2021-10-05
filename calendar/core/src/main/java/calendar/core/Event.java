package calendar.core;

import java.time.LocalDate;

public class Event {
    private String header;
    private String description;
    private LocalDate date;

    public Event(String headerArg, String descArg, LocalDate dateArg) {
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

    public void setDate(LocalDate newDate) {
        this.date = newDate; 
    }

    public String getHeader() {
        return this.header;
    }

    public String getDescription() {
        return this.description;
    }

    public LocalDate getDate() {
        return this.date; 
    }
}


