package it1901.calendar.rest;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import calendar.core.Calendar;
import calendar.core.Event;
import calendar.core.RequestEvent;
import calendar.core.User;
import calendar.json.UserPersistence;

@Service
public class UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private static Map<String, User> userRepo = new HashMap<>();

    private UserPersistence userPersistence = new UserPersistence();

    public User getUser(String username) throws IllegalStateException, FileNotFoundException, IOException {
        userPersistence.setSaveFile(username + ".json");

        if (userPersistence.loadUser() == null) {
            userRepo.put(username, new User(username));
        }
        userRepo.put(username, userPersistence.loadUser());
        autoSaveUser(userRepo.get(username));

        return userRepo.get(username);
    }

    public Calendar getCalendar(String username) {
        if (userRepo.get(username) == null) {
            throw new IllegalArgumentException("No user with username " + username);
        }
        return userRepo.get(username).getCalendar();
    }

    public Event getEvent(String username, int id) {
        if (userRepo.get(username) == null) {
            throw new IllegalArgumentException("No user with username " + username);
        }
        if (userRepo.get(username).getCalendar().getEvent(id) == null) {
            throw new IllegalArgumentException("No event with id " + id);
        }
        return userRepo.get(username).getCalendar().getEvent(id);
    }

    public Event replaceEvent(RequestEvent event, String username, int id) {
        if (userRepo.get(username) == null) {
            throw new IllegalArgumentException("No user with username " + username);
        }
        Event newEvent = new Event();
        newEvent.setHeader(event.getHeader());
        newEvent.setDescription(event.getDescription());
        newEvent.setId(id);
        newEvent.setTime(event.getTime());
        newEvent.setDate(event.getLocalDate());
        userRepo.get(username).getCalendar().replaceEvent(newEvent);
        autoSaveUser(userRepo.get(username));
        return newEvent;

    }

    public Event postEvent(RequestEvent event, String username) {
        if (userRepo.get(username) == null) {
            throw new IllegalArgumentException("No user with username " + username);
        }
        Event newEvent = new Event();
        newEvent.setHeader(event.getHeader());
        newEvent.setDescription(event.getDescription());
        newEvent.setTime(event.getTime());
        newEvent.setDate(event.getLocalDate());
        userRepo.get(username).getCalendar().addEvent(newEvent);
        autoSaveUser(userRepo.get(username));
        return newEvent;
    }

    public Event deleteEvent(String username, int id) {
        if (userRepo.get(username) == null) {
            throw new IllegalArgumentException("No user with username " + username);
        }
        if (userRepo.get(username).getCalendar().getEvent(id) == null) {
            throw new IllegalArgumentException("No event with id " + id);
        }
        Event event = userRepo.get(username).getCalendar().deleteEvent(id);
        autoSaveUser(userRepo.get(username));
        return event;
    }

    public void addUser(User user) {
        userRepo.put(user.getUsername(), user);
    }

    public void addEvent(String username, Event event) {
        if (userRepo.get(username) == null) {
            throw new IllegalArgumentException("No user with username " + username);
        }
        userRepo.get(username).getCalendar().addEvent(event);
        autoSaveUser(userRepo.get(username));
    }

    public User findUser(String username) {
        if (userRepo.get(username) == null) {
            throw new IllegalArgumentException("No user with username " + username);
        }
        return userRepo.get(username);
    }

    public void autoSaveUser(User user) {
        try {
            userPersistence.setSaveFile(user.getUsername() + ".json");
            userPersistence.saveUser(user);
        } catch (IllegalStateException | IOException e) {
            LOG.error("Could not save user " + e);
        }
    }

    public void setUserPersistence(UserPersistence userPersistence) {
        this.userPersistence = userPersistence;
    }

}
