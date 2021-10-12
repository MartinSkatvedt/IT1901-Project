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

    
    public void  login(ActionEvent event) throws IOException {
        if (username.getText().toString().isEmpty()) {
            loginReply.setText("Please input username");
        }
        else {

            UserPersistence userPersistence = new UserPersistence();
            userPersistence.setSaveFile(username.getText());
            User currentUser = userPersistence.loadUser();
            if (currentUser != null)  loginReply.setText(currentUser.getUsername());
            else {
                loginReply.setText("Fant ikke fil");
                currentUser = new User(username.getText());
                userPersistence.saveUser(currentUser);
            }
            /*
            Stage stage = (Stage) loginButton.getScene().getWindow();
            loginReply.setText("Welcome");
            Parent root = FXMLLoader.load(getClass().getResource("Calendar.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();*/
        }
    }

   
}
