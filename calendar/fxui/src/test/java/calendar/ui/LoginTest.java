package calendar.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
//import javafx.scene.control.ListView;
//import javafx.scene.control.TextField;
import javafx.stage.Stage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
//import java.util.List;
//import java.util.stream.Stream;

//import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.Arguments;
//import org.junit.jupiter.params.provider.MethodSource;
//import org.junit.jupiter.params.provider.EmptySource;
import org.testfx.framework.junit5.ApplicationTest;
//import org.testfx.matcher.control.LabeledMatchers;

/**
 * TestFX App test
 */

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
        // given started application and opened login dialog
        sleep(1000);
        clickOn("#loginButton");
        assertEquals("Please input username", getLabelString());
        sleep(1000);
        clickOn("#username");
        write("Mohammed");
        clickOn("#loginButton");
}
    
}
