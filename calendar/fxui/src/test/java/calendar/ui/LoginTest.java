package calendar.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;


public class LoginTest extends ApplicationTest {

    private LoginController controller;
    private Parent root;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("Login.fxml"));
        root = fxmlLoader.load();
        controller = fxmlLoader.getController();
        stage.setScene(new Scene(root));
        stage.show();
    }


    public Parent getRootNode() {
        return root;
    }

    private String getLabelString() {
        return ((Label) getRootNode().lookup("#loginReply")).getText();
    }
    
    @Test
    public void successfulLogin() {
        sleep(1000);
        clickOn("#loginButton");
        assertEquals("Please input username", getLabelString());
        sleep(1000);
        clickOn("#username");
        write("Ola");
        clickOn("#loginButton");

        
}
    
}

