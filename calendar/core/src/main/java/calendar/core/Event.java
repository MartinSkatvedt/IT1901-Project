package calendar.core;

public class Event {
    private String header;
    private String description;

    public Event(String headerArg, String descArg) {
        this.header = headerArg;
        this.description = descArg;
    }

    public void setHeader(String newHeader) {
        this.header = newHeader;
    }


    public void setDescription(String newDesc) {
        this.description = newDesc;
    }

    public String getHeader() {
        return this.header;
    }

    public String getDescription() {
        return this.description;
    }
}


