package todolist.springboot.restserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import todolist.core.AbstractTodoList;
import todolist.core.TodoModel;

@RestController
@RequestMapping(TodoModelController.TODO_MODEL_SERVICE_PATH)
public class TodoModelController {

  public static final String TODO_MODEL_SERVICE_PATH = "springboot/todo";

  @Autowired
  private TodoModelService todoModelService;

  @GetMapping
  public TodoModel getTodoModel() {
    return todoModelService.getTodoModel();
  }

  private void autoSaveTodoModel() {
    todoModelService.autoSaveTodoModel();
  }

  private void checkTodoList(AbstractTodoList todoList, String name) {
    if (todoList == null) {
      throw new IllegalArgumentException("No TodoList named \"" + name + "\"");
    }
  }

  /**
   * Gets the corresponding TodoList.
   *
   * @param name the name of the TodoList
   * @return the corresponding TodoList
   */
  @GetMapping(path = "/{name}")
  public AbstractTodoList getTodoList(@PathVariable("name") String name) {
    AbstractTodoList todoList = getTodoModel().getTodoList(name);
    checkTodoList(todoList, name);
    return todoList;
  }

  /**
   * Replaces or adds a TodoList.
   *
   * @param name the name of the TodoList
   * @param todoList the todoList to add
   * @return true if it was added, false if it replaced
   */
  @PutMapping(path = "/{name}")
  public boolean putTodoList(@PathVariable("name") String name,
      @RequestBody AbstractTodoList todoList) {
    boolean added = getTodoModel().putTodoList(todoList) == null;
    autoSaveTodoModel();
    return added;
  }

  /**
   * Renames the TodoList.
   *
   * @param name the name of the TodoList
   * @param newName the new name
   */
  @PostMapping(path = "/{name}/rename")
  public boolean renameTodoList(@PathVariable("name") String name,
      @RequestParam("newName") String newName) {
    AbstractTodoList todoList = getTodoModel().getTodoList(name);
    checkTodoList(todoList, name);
    if (getTodoModel().getTodoList(newName) != null) {
      throw new IllegalArgumentException("A TodoList named \"" + newName + "\" already exists");
    }
    todoList.setName(newName);
    autoSaveTodoModel();
    return true;
  }

  /**
   * Removes the TodoList.
   *
   * @param name the name of the TodoList
   */
  @DeleteMapping(path = "/{name}")
  public boolean removeTodoList(@PathVariable("name") String name) {
    AbstractTodoList todoList = getTodoModel().getTodoList(name);
    checkTodoList(todoList, name);
    getTodoModel().removeTodoList(todoList);
    autoSaveTodoModel();
    return true;
  }
}
