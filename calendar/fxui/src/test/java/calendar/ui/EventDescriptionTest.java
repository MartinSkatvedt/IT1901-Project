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

import calendar.core.User;

/**
 * TestFX App test
 */


public class EventDescriptionTest extends ApplicationTest {

    private EventController controller;
    private Parent root;
    private User currentUser;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("calendar/ui/EventDescription.fxml"));
        CalendarController calendarController = new CalendarController();
        calendarController.setUser(currentUser);
        loader.setController(calendarController);
    
        Parent root = loader.load();
        Scene scene = new Scene(root); 
        stage.setScene(scene);
        stage.show();
    }


    public Parent getRootNode() {
        return root;
    }

    
    @Test
    public void testEventMaker() {
        sleep(1000);
    }
}
        