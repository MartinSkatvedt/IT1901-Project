package calendar.core;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String username;
    private Calendar calendar;
    private List<User> friends = new ArrayList<>();

    public User(final String username) {
        // Sjekke om det er gyldig brukernavn?
        this.username = username;
        this.calendar = new Calendar();
    }

    public String getUsername() {
        return username;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void addFriend(User newFriend) {
        if (friends.contains(newFriend)) {
            throw new IllegalArgumentException("This user is already a friend");
        }
        friends.add(newFriend);
    }

    public Calendar getFriendsCalendar(User friend) {
        if (!friends.contains(friend)) {
            throw new IllegalArgumentException("This user is not a friend");
        }
        return friend.getCalendar();
    }

    public void resetCalendar() {
        this.calendar = new Calendar();
    }

}
