package todolist.ui;

import java.util.ArrayList;
import java.util.Collection;
import todolist.core.AbstractTodoList;
import todolist.core.TodoModel;
import todolist.json.TodoPersistence;

/**
 * Class that centralizes access to a TodoModel.
 * Makes it easier to support transparent use of a REST API.
 */
public class DirectTodoModelAccess implements TodoModelAccess {

  private final TodoModel todoModel;

  public DirectTodoModelAccess(TodoModel todoModel) {
    this.todoModel = todoModel;
  }

  /**
   * Checks that name is valid for a (new) TodoList.
   *
   * @param name the (new) name
   * @return true if the name is value, false otherwise
   */
  @Override
  public boolean isValidTodoListName(String name) {
    return todoModel.isValidTodoListName(name);
  }

  /**
   * Checks if there (already) exists a TodoList with the provided name.
   *
   * @param name the (new) name
   * @return true if there exists a TodoList with the provided name, false otherwise
   */
  @Override
  public boolean hasTodoList(String name) {
    return todoModel.hasTodoList(name);
  }

  /**
   * Gets the names of the TodoLists.
   *
   * @return the names of the TodoLists.
   */
  @Override
  public Collection<String> getTodoListNames() {
    Collection<String> allNames = new ArrayList<>();
    todoModel.forEach(todoList -> allNames.add(todoList.getName()));
    return allNames;
  }

  /**
   * Gets the TodoList with the given name.
   *
   * @param name the TodoList's name
   * @return the TodoList with the given name
   */
  @Override
  public AbstractTodoList getTodoList(String name) {
    return todoModel.getTodoList(name);
  }

  /**
   * Adds a TodoList to the underlying TodoModel.
   *
   * @param todoList the TodoList
   */
  @Override
  public void addTodoList(AbstractTodoList todoList) {
    todoModel.addTodoList(todoList);
  }

  /**
   * Removes the TodoList with the given name from the underlying TodoModel.
   *
   * @param name the name of the TodoList to remove
   */
  @Override
  public void removeTodoList(String name) {
    todoModel.removeTodoList(todoModel.getTodoList(name));
  }

  /**
   * Renames a TodoList to a new name.
   *
   * @param oldName the name of the TodoList to change
   * @param newName the new name
   */
  @Override
  public void renameTodoList(String oldName, String newName) {
    AbstractTodoList todoList = todoModel.getTodoList(oldName);
    if (todoList == null) {
      throw new IllegalArgumentException("No TodoList named \"" + newName + "\" found");
    }
    if (todoModel.getTodoList(newName) != null) {
      throw new IllegalArgumentException("A TodoList named \"" + newName + "\" already exists");
    }
    todoList.setName(newName);
  }

  /**
   * Notifies that the TodoList has changed, e.g. TodoItems
   * have been mutated, added or removed.
   *
   * @param todoList the TodoList that has changed
   */
  @Override
  public void notifyTodoListChanged(AbstractTodoList todoList) {
    if (autosaveOn) {
      autoSaveTodoModel();
    }
  }

  private boolean autosaveOn = false;

  public void setAutosaveOn(boolean autosaveOn) {
    this.autosaveOn = autosaveOn;
  }

  private TodoPersistence todoPersistence = null;

  public void setTodoPersistence(TodoPersistence todoPersistence) {
    this.todoPersistence = todoPersistence;
  }

  private void autoSaveTodoModel() {    
    if (todoPersistence != null) {
      try {
        todoPersistence.saveTodoModel(todoModel);
      } catch (Exception e) {
        System.err.println("Fikk ikke lagret TodoModel: " + e.getMessage());
      }
    }
  }
}