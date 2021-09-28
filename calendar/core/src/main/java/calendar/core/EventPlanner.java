package calendar.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class EventPlanner {

    private final Collection<User> allUsers = new ArrayList<>();

    public EventPlanner() {

    }

    private boolean userNameTaken(String userName) {
        return allUsers.stream().anyMatch(user -> user.getUsername().equals(userName));
    }

    public User getUser(String userName) {
        if (!userNameTaken(userName)) {
            throw new IllegalArgumentException("User does not exist.");
        }
        return allUsers.stream().filter(user -> user.getUsername().equals(userName)).findAny().get();
    }

    public void addNewUser(String userName) {
        if (userNameTaken(userName)) {
            throw new IllegalArgumentException("Username is taken.");
        }
        allUsers.add(new User(userName));
    }

    public void removeUser(String userName) {
        allUsers.remove(getUser(userName));
    }

    public Collection<User> getAllUsers() {
        return allUsers.stream().collect(Collectors.toList());
    }
}
