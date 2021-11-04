package calendar.ui;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CalendarApp extends Application {
    private Stage stage;
    
    @Override
    public void start(Stage firstStage) throws Exception {
        this.stage = firstStage;
        firstStage.setResizable(false);
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        firstStage.setTitle("CoolCalendar");
        firstStage.setScene(new Scene(root, 350, 400));
        firstStage.show();
    }
    
    public void changeScene(String fxml) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource(fxml));
        Scene scene = new Scene(parent, 600, 500);
        this.stage.setScene(scene);
    } 

    public static void main(String[] args) {
        launch(CalendarApp.class, args);
    }
}
