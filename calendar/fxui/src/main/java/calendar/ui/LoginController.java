package calendar.ui;


import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class LoginController {

    public LoginController(){

    }

    @FXML private Label loginReply;

    @FXML private TextField username;

    @FXML private PasswordField password;

    @FXML private Button loginButton;

    @FXML private Button registerButton;

    @FXML private TextField registerUsername;

    @FXML private TextField registerPassword;

    @FXML private Button registerUserButton;

    
    public void  login(ActionEvent event) throws IOException {
        if (username.getText().toString().isEmpty() || password.getText().isEmpty()) {
            loginReply.setText("Please input username and password");
        }

        else {
            Stage stage = (Stage) loginButton.getScene().getWindow();
            loginReply.setText("Welcome");
            Parent root = FXMLLoader.load(getClass().getResource("Calendar.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            //userAccess(); 
        }
    }

    
    public void register(ActionEvent event) throws IOException {
        Stage stage = (Stage) registerButton.getScene().getWindow();
            loginReply.setText("Let´s get started!");
            Parent root = FXMLLoader.load(getClass().getResource("RegistrationForm.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

    }
    
    
   // public void registerUser(ActionEvent event) throws IOException {
    //    if (username.getText().toString().isEmpty() || password.getText().isEmpty()) {
    //}
}

