package calendar.core;

import java.util.Date;

public class Event {
    private String header;
    private String description;
    private Date date;

    public Event(String headerArg, String descArg, Date dateArg) {
        this.header = headerArg;
        this.description = descArg;
        this.date = dateArg;
    }

    public void setHeader(String newHeader) {
        this.header = newHeader;
    }

    public void setDescription(String newDesc) {
        this.description = newDesc;
    }

    public void setDate(Date newDate) {
        this.date = newDate;
    }

    public String getHeader() {
        return this.header;
    }

    public String getDescription() {
        return this.description;
    }

    public Date getDate() {
        return this.date;
    }
}


