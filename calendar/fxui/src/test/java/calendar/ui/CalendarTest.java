package calendar.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;




public class CalendarTest extends ApplicationTest {

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

    
    @BeforeEach
    public void logIn() {
        clickOn("#username");
        write("Ola");
        clickOn("#loginButton");
    } 

    @Test
    public void testMonthShift() {
        clickOn("#next_month"); 
        clickOn("#prev_month");
        clickOn("#prev_month");
        clickOn("#today");
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


