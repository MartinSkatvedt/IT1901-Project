package calendar.core;

import java.time.LocalDate;

/**
 * Class that contains an event with header, description, date, time
 */
public class Event {
    private String header;
    private String description;
    private LocalDate date;
    private String time;
    private int id;

    /**
     * Constructor which sets the inital values for the event
     * 
     * @param headerArg header for the event
     * @param descArg   description for the event
     * @param dateArg   date for the event
     * @param timeArg   time for the event
     */
    public Event(String headerArg, String descArg, LocalDate dateArg, String timeArg) {
        this.setHeader(headerArg);
        this.setDescription(descArg);
        this.setDate(dateArg);
        this.setTime(timeArg);
        this.id = 0;
    }

    public Event() {
        this.id = 0;
    }

    /**
     * Sets the header for the event
     * 
     * @param newHeader the new header for the event
     */
    public void setHeader(String newHeader) {
        if (newHeader.replaceAll("\\s", "").isEmpty()) {
            throw new IllegalArgumentException("New Header cannot be empty");
        }
        this.header = newHeader;
    }

    /**
     * Sets the description for the event
     * 
     * @param newDesc the new description for the event
     */
    public void setDescription(String newDesc) {
        this.description = newDesc;
    }

    /**
     * Sets the date for the event
     * 
     * @param newDate the new date for the event
     */
    public void setDate(LocalDate newDate) {
        this.date = newDate;
    }

    /**
     * Sets the time for the event
     * 
     * @param newTime the new time for the event
     */
    public void setTime(String newTime) {
        if (newTime.replaceAll("\\s", "").isEmpty()) {
            throw new IllegalArgumentException("New Time cannot be empty");
        }
        this.time = newTime;
    }

    /**
     * Returns the header
     * 
     * @return header for event
     */

    public String getHeader() {
        return this.header;
    }

    /**
     * Returns the description
     * 
     * @return description for event
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns the date of the event
     * 
     * @return date for event (LocalDate)
     */
    public LocalDate getDate() {
        return this.date;
    }

    /**
     * Returns the timestring of the event
     * 
     * @return timestring for event
     */
    public String getTimeString() {
        return this.time;
    }

    /**
     * Returns the time hour
     * 
     * @return hour (Integer)
     */
    public Integer getTimeHour() {
        String timeString[] = getTimeString().split(":");
        String hourString = timeString[0];
        if (hourString.charAt(0) == '0') {
            return Integer.valueOf(hourString.substring(1));
        }
        return Integer.valueOf(hourString);
    }

    /**
     * Returns the minute hour
     * 
     * @return minute (Integer)
     */
    public Integer getTimeMinute() {
        String timeString[] = getTimeString().split(":");
        String minuteString = timeString[1];
        if (minuteString.charAt(0) == '0') {
            return Integer.valueOf(minuteString.substring(1));
        }
        return Integer.valueOf(minuteString);
    }

    /**
     * Sets a int id
     * 
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the int id
     * 
     * @return id (int)
     */
    public int getId() {
        return this.id;
    }
}
