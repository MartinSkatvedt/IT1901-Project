package todolist.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import todolist.json.TodoPersistence;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

public class TodoAppTest extends ApplicationTest {

  private TodoModelController controller;

  @Override
  public void start(final Stage stage) throws Exception {
    final FXMLLoader loader = new FXMLLoader(getClass().getResource("TodoModel_test.fxml"));
    final Parent root = loader.load();
    this.controller = loader.getController();
    stage.setScene(new Scene(root));
    stage.show();
  }
  
  private TodoPersistence todoPersistence = new TodoPersistence();

  @BeforeEach
  public void setupItems() {
    // same as in test-todolist.json (should perhaps read it instead)
    try (Reader reader = new InputStreamReader(getClass().getResourceAsStream("test-todomodel.json"))) {
      this.controller.setTodoModelAccess(new DirectTodoModelAccess(todoPersistence.readTodoModel(reader)));
    } catch (IOException ioe) {
      fail(ioe.getMessage());
    }
  }

  @Test
  public void testController_initial() {
    assertNotNull(this.controller);
  }

  @Test
  public void testSelectedTodoList_initial() {
    assertNotNull(this.controller.getSelectedTodoList());
  }
}
