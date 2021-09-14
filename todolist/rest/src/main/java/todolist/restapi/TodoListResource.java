package todolist.restapi;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import todolist.core.AbstractTodoList;
import todolist.core.TodoList;
import todolist.core.TodoModel;
import todolist.json.TodoPersistence;

/**
 * Used for all requests referring to a TodoList by name.
 */
@Produces(MediaType.APPLICATION_JSON)
public class TodoListResource {

  private static final Logger LOG = LoggerFactory.getLogger(TodoListResource.class);

  private final TodoModel todoModel;
  private final String name;
  private final AbstractTodoList todoList;

  @Context
  private TodoPersistence todoPersistence;

  public void setTodoPersistence(TodoPersistence todoPersistence) {
    this.todoPersistence = todoPersistence;
  }

  /**
   * Initializes this TodoListResource with appropriate context information. Each method will check
   * and use what it needs.
   *
   * @param todoModel the TodoModel, needed for DELETE and rename
   * @param name      the todo list name, needed for most requests
   * @param todoList  the TodoList, or null, needed for PUT
   */
  public TodoListResource(TodoModel todoModel, String name, AbstractTodoList todoList) {
    this.todoModel = todoModel;
    this.name = name;
    this.todoList = todoList;
  }

  private void checkTodoList() {
    if (this.todoList == null) {
      throw new IllegalArgumentException("No TodoList named \"" + name + "\"");
    }
  }

  /**
   * Gets the corresponding TodoList.
   *
   * @return the corresponding TodoList
   */
  @GET
  public AbstractTodoList getTodoList() {
    checkTodoList();
    LOG.debug("getTodoList({})", name);
    return this.todoList;
  }

  private void autoSaveTodoModel() {
    if (todoPersistence != null) {
      try {
        todoPersistence.saveTodoModel(todoModel);
      } catch (IllegalStateException | IOException e) {
        System.err.println("Couldn't auto-save TodoModel: " + e);
      }
    }
  }

  /**
   * Replaces or adds a TodoList.
   *
   * @param todoListArg the todoList to add
   * @return true if it was added, false if it replaced
   */
  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  public boolean putTodoList(AbstractTodoList todoListArg) {
    LOG.debug("putTodoList({})", todoListArg);
    AbstractTodoList oldTodoList = this.todoModel.putTodoList(todoListArg);
    autoSaveTodoModel();
    return oldTodoList == null;
  }

  /**
   * Adds a TodoList with the given name, if it doesn't exist already.
   *
   * @return true if it was added, false if it replaced
   */
  @PUT
  public boolean putTodoList() {
    return putTodoList(new TodoList(name));
  }

  /**
   * Renames the TodoList.
   *
   * @param newName the new name
   */
  @POST
  @Path("/rename")
  public boolean renameTodoList(@QueryParam("newName") String newName) {
    checkTodoList();
    if (this.todoModel.getTodoList(newName) != null) {
      throw new IllegalArgumentException("A TodoList named \"" + newName + "\" already exists");
    }
    this.todoList.setName(newName);
    autoSaveTodoModel();
    return true;
  }

  /**
   * Removes the TodoList.
   */
  @DELETE
  public boolean removeTodoList() {
    checkTodoList();
    this.todoModel.removeTodoList(this.todoList);
    autoSaveTodoModel();
    return true;
  }
}
