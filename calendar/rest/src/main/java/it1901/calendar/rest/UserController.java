package it1901.calendar.rest;

import java.io.IOException;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


import calendar.core.User;
import calendar.json.UserPersistence;

    /**
     * Gets exiting user or creates a new one
     */

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    
    private User user;
    private UserPersistence userPersistence;        
        

    public UserController() {
        this.userPersistence = new UserPersistence();
    }

    /**
     * Get a specific user based on username
     * 
     * @param username username of user
     * @return the user 
     */
    @GetMapping ("/user/{username}")
    public ResponseEntity<User> getUser(@PathVariable String username){
        try {
            userPersistence.setSaveFile(username + ".json");
            if (userPersistence.loadUser() == null){
                this.user = new User(username);
                userPersistence.saveUser(this.user);
            }
            else {
                this.user = userPersistence.loadUser();
            }
            return ResponseEntity.ok(user);
        }
        catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
        
}


