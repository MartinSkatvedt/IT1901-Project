package todolist.restserver;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import todolist.core.TodoList;
import todolist.core.TodoModel;
import todolist.json.TodoPersistence;
import todolist.restapi.TodoModelService;

/**
 * Configures the rest service,
 * e.g. JSON support with Jackson and
 * injectable TodoModel and TodoPersistance
 */
public class TodoConfig extends ResourceConfig {

  private TodoModel todoModel;
  private TodoPersistence todoPersistence;

  /**
   * Initialize this TodoConfig.
   *
   * @param todoModel todoModel instance to serve
   */
  public TodoConfig(TodoModel todoModel) {
    setTodoModel(todoModel);
    todoPersistence = new TodoPersistence();
    todoPersistence.setSaveFile("server-todolist.json");
    register(TodoModelService.class);
    register(TodoModuleObjectMapperProvider.class);
    register(JacksonFeature.class);
    register(new AbstractBinder() {
      @Override
      protected void configure() {
        bind(TodoConfig.this.todoModel);
        bind(TodoConfig.this.todoPersistence);
      }
    });
  }

  /**
   * Initialize this TodoConfig with a default TodoModel.
   */
  public TodoConfig() {
    this(createDefaultTodoModel());
  }

  public TodoModel getTodoModel() {
    return todoModel;
  }

  public void setTodoModel(TodoModel todoModel) {
    this.todoModel = todoModel;
  }

  private static TodoModel createDefaultTodoModel() {
    TodoPersistence todoPersistence = new TodoPersistence();
    URL url = TodoConfig.class.getResource("default-todomodel.json");
    if (url != null) {
      try (Reader reader = new InputStreamReader(url.openStream(), StandardCharsets.UTF_8)) {
        return todoPersistence.readTodoModel(reader);
      } catch (IOException e) {
        System.out.println("Couldn't read default-todomodel.json, so rigging TodoModel manually ("
            + e + ")");
      }
    }
    TodoModel todoModel = new TodoModel();
    todoModel.addTodoList(new TodoList("todo1"));
    todoModel.addTodoList(new TodoList("todo2"));
    return todoModel;
  }
}
