package calendar.core;

import java.time.LocalDate;

public class RequestEvent {

    private String header;
    private String description;
    private int id;
    private String date;
    private String time;

    public RequestEvent() {

    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHeader() {
        return header;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public String getDateString() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public LocalDate getLocalDate() {
        return LocalDate.parse(getDateString());
    }

}
