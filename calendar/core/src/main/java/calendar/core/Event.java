package calendar.core;

import java.time.LocalDate;

public class Event implements Comparable<Event> {
    private String header;
    private String description;
    private LocalDate date;
    private String time;

    public Event(String headerArg, String descArg, LocalDate dateArg, String timeArg) {
        this.setHeader(headerArg);
        this.setDescription(descArg);
        this.setDate(dateArg);
        this.setTime(timeArg);
    }

    public void setHeader(String newHeader) {
        if (newHeader.replaceAll("\\s", "").isEmpty())
            throw new IllegalArgumentException("New Header cannot be empty");
        this.header = newHeader;
    }

    public void setDescription(String newDesc) {
        if (newDesc.replaceAll("\\s", "").isEmpty())
            throw new IllegalArgumentException("New Description cannot be empty");
        this.description = newDesc;
    }

    public void setDate(LocalDate newDate) {
        this.date = newDate;
    }

    public void setTime(String newTime) {
        this.time = newTime;
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

    public String getTimeString() {
        return this.time;
    }

    public Integer getTimeHour() {
        String timeString[] = getTimeString().split(":");
        String hourString = timeString[0];
        if (hourString.charAt(0) == '0') {
            return (int) hourString.charAt(1);
        }
        return Integer.valueOf(hourString);
    }

    public Integer getTimeMinute() {
        String timeString[] = getTimeString().split(":");
        String minuteString = timeString[1];
        if (minuteString.charAt(0) == '0') {
            return (int) minuteString.charAt(1);
        }
        return Integer.valueOf(minuteString);
    }

    @Override
    public int compareTo(Event o) {
        if (this.getTimeHour() == o.getTimeHour()) {
            return this.getTimeMinute() - o.getTimeMinute();
        }
        return this.getTimeHour() - o.getTimeHour();
    }
}
