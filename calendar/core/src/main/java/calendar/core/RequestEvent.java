package calendar.core;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

/**
 * A placeholder for events with date represented as string. Used for PUT and
 * POST requests.
 */

public class RequestEvent {

    private String header;
    private String description;
    private String date;
    private String time;

    public RequestEvent() {

    }

    /**
     * Sets events header
     * 
     * @param header header to be set (String)
     */
    public void setHeader(String header) {
        this.header = header;
    }

    /**
     * Sets events description
     * 
     * @param description description to be set (String)
     */
    public void setDescription(String description) {
        this.description = description;
    }


    /**
     * Sets events date
     * 
     * @param date date to be set (String)
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Sets events time
     * 
     * @param time time to be set (String)
     */
    public void setTime(String time) {
        if (time.replaceAll("\\s", "").isEmpty()) {
            throw new IllegalArgumentException("New Time cannot be empty");
        }
        if (!time.matches("([01]?[0-9]|2[0-3]):[0-5][0-9]")) {
            throw new IllegalArgumentException("Invalid string format");
        }
        this.time = time;
    }

    /**
     * Gets events header
     * 
     * @return header (String)
     */
    public String getHeader() {
        return header;
    }

    /**
     * Gets events description
     * 
     * @return description (String)
     */
    public String getDescription() {
        return description;
    }


    /**
     * Gets events date as string
     * 
     * @return date (String)
     */
    public String getDateString() {
        return date;
    }

    /**
     * Gets events time
     * 
     * @return time (String)
     */
    public String getTime() {
        return time;
    }

    /**
     * Gets events date as LocalDate
     * 
     * @return date as LocalDate
     */
    public LocalDate getLocalDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(getDateString(), formatter);
    }

}
