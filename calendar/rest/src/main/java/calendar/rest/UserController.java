package calendar.rest;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import calendar.core.User;

/**
 * Gets exiting user or creates a new one
 */

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService = new UserService();

    public UserController() {

    }

    /**
     * Get a specific user based on username
     * 
     * @param username username of user
     * @return the user
     */
    @GetMapping("/user/{username}")
    public ResponseEntity<User> getUser(@PathVariable String username) {
        User user;
        try {
            user = userService.getUser(username);
        } catch (IllegalStateException | IOException e) {
            LOG.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(user);

    }

}
