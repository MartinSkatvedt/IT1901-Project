package todolist.springboot.restserver;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import org.springframework.stereotype.Service;
import todolist.core.TodoList;
import todolist.core.TodoModel;
import todolist.json.TodoPersistence;

@Service
public class TodoModelService {

  private TodoModel todoModel;
  private TodoPersistence todoPersistence;

  public TodoModelService(TodoModel todoModel) {
    this.todoModel = todoModel;
    this.todoPersistence = new TodoPersistence();
    todoPersistence.setSaveFile("springbootserver-todolist.json");
  }

  public TodoModelService() {
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
    URL url = TodoModelService.class.getResource("default-todomodel.json");
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

  public void autoSaveTodoModel() {
    if (todoPersistence != null) {
      try {
        todoPersistence.saveTodoModel(this.todoModel);
      } catch (IllegalStateException | IOException e) {
        System.err.println("Couldn't auto-save TodoModel: " + e);
      }
    }
  }
}