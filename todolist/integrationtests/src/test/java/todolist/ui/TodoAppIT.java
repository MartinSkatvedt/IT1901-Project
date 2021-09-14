package todolist.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

public class TodoAppIT extends ApplicationTest {

  private TodoModelController controller;

  @Override
  public void start(final Stage stage) throws Exception {
    final FXMLLoader loader = new FXMLLoader(getClass().getResource("TodoApp_it.fxml"));
    final Parent root = loader.load();
    this.controller = loader.getController();
    stage.setScene(new Scene(root));
    stage.show();
  }

  @BeforeEach
  public void setupItems() throws URISyntaxException {
    // same as in test-todolist.json (should perhaps read it instead)
    try (Reader reader = new InputStreamReader(getClass().getResourceAsStream("it-todomodel.json"))) {
      String port = System.getProperty("todo.port");
      assertNotNull(port, "No todo.port system property set");
      URI baseUri = new URI("http://localhost:" + port + "/todo/");
      System.out.println("Base RemoteTodoModelAcces URI: " + baseUri);
      this.controller.setTodoModelAccess(new RemoteTodoModelAccess(baseUri));
    } catch (IOException ioe) {
      fail(ioe.getMessage());
    }
  }

  @Test
  public void testController_initial() {
    assertNotNull(this.controller);
  }
}
