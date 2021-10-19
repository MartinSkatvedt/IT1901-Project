package calendar.core;


/**
 * User class which contains a username and a calendar
 */
public class User {
    private String username;
    private Calendar calendar;


    /**
     * Constructor for user which sets the username
     * @param username username for user
     */
    public User(final String username) {
        this.username = username;
        this.calendar = new Calendar();
    }

    /**
     * Returns the username of the user
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the calendar of the user
     * @return calendar
     */
    public Calendar getCalendar() {
        return calendar;
    }

    /**
     * Resets the calendar for the user
     */
    public void resetCalendar() {
        this.calendar = new Calendar();
    }
}
