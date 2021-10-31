package it1901.calendar.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import calendar.core.Calendar;
import calendar.core.User;

/**
 * Used for requests for a users calendar PUT for adding a event POST for
 * editing event DELETE for deleting a event
 */

@RestController
@RequestMapping("/calendar")
public class CalendarController {

    private Calendar calendar;

    @PutMapping(path = "/{header}", consumes = "application/json", produces = "application/json")
    public boolean putEvent() {
        return true;
    }

}
