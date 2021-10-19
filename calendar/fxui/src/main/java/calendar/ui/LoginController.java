package calendar.ui;


import java.io.IOException;

import calendar.core.User;
import calendar.json.UserPersistence;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class LoginController {

    public LoginController(){

    }

    @FXML private Label loginReply;

    @FXML private TextField username;

    @FXML private Button loginButton;

    @FXML private Button registerButton;

    @FXML private TextField registerUsername;

    @FXML private TextField registerPassword;

    @FXML private Button registerUserButton;

    
    /**
     * Logs user in
     * @param event ActionEvent from javafx button
     */
    public void login(ActionEvent event) throws IOException {
        if (username.getText().toString().isEmpty()) {
            loginReply.setText("Please input username");
            return;
        }
        
        UserPersistence userPersistence = new UserPersistence();
        userPersistence.setSaveFile(username.getText() + ".json");
        User currentUser = userPersistence.loadUser();

        if (currentUser != null)  {
            loginReply.setText(currentUser.getUsername());
        }
        else {
            currentUser = new User(username.getText());
            userPersistence.saveUser(currentUser);
        }

        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.close();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("calendar/ui/Calendar.fxml"));
        CalendarController calendarController = new CalendarController();
        calendarController.setUser(currentUser);
        loader.setController(calendarController);
    
        Parent root = loader.load();
        Scene scene = new Scene(root); 
        stage.setScene(scene);
        stage.show();
    }
}

