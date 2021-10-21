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
import java.time.LocalDate;

import org.eclipse.jetty.io.ssl.SslClientConnectionFactory;
import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.Arguments;
//import org.junit.jupiter.params.provider.MethodSource;
//import org.junit.jupiter.params.provider.EmptySource;
import org.testfx.framework.junit5.ApplicationTest;
//import org.testfx.matcher.control.LabeledMatchers;



public class CalendarTest extends ApplicationTest {

    private LoginController controller;
    private Parent root;
    //private User user = new User("egil");

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

    /* monthLabel er null??
    private String checkMonthLabel() {
        return ((Label) getRootNode().lookup("#monthLabel")).getText();
    }
    */
    
    @BeforeEach
    public void logIn() {
        clickOn("#username");
        write("Mohammed");
        clickOn("#loginButton");
    } 

    @Test
    public void testMonthShift() {
        /*
        LocalDate currentDate = LocalDate.now();
        int month = currentDate.getMonthValue();
        int year = currentDate.getYear();
        String[] monthsList = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        String monthYear = monthsList[month -1] + " " + year;

        System.out.println("monthLabel:" + checkMonthLabel());
        */

        clickOn("#next_month");
        //assertEquals(monthYear, checkMonthLabel());  
        clickOn("#prev_month");
        clickOn("#prev_month");
        
        
    }
    

    @Test
    public void testCreateEvent(){
        clickOn("#newEvent");
        clickOn("#cancel");
        clickOn("#newEvent");
        clickOn("#title").write("Party");
        clickOn("#date").write("30.10.2021");
        clickOn("#hours");
        clickOn("#hours");
        clickOn("#minutes");
        clickOn("#minutes");
        clickOn("#description").write("crazy bananas party at Tryms house");
        clickOn("#addEvent");
        
    }
    
    
    
}


