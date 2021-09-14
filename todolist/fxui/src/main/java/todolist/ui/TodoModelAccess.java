package todolist.ui;

import java.util.Collection;
import todolist.core.AbstractTodoList;

/**
 * Class that centralizes access to a TodoModel.
 * Makes it easier to support transparent use of a REST API.
 */
public interface TodoModelAccess {

  /**
   * Checks that name is valid for a (new) TodoList.
   *
   * @param name the (new) name
   * @return true if the name is valid, false otherwise
   */
  public boolean isValidTodoListName(String name);

  /**
   * Checks if there (already) exists a TodoList with the provided name.m
   *
   * @param name the (new) name
   * @return true if there exists a TodoList with the provided name, false otherwise
   */
  public boolean hasTodoList(String name);

  /**
   * Gets the names of the TodoLists.
   *
   * @return the names of the TodoLists.
   */
  Collection<String> getTodoListNames();

  /**
   * Gets the TodoList with the given name.
   *
   * @param name the TodoList's name
   * @return the TodoList with the given name
   */
  AbstractTodoList getTodoList(String name);

  /**
   * Adds a TodoList to the underlying TodoModel.
   *
   * @param todoList the TodoList
   */
  void addTodoList(AbstractTodoList todoList);

  /**
   * Removes the TodoList with the given name from the underlying TodoModel.
   *
   * @param name the name of the TodoList to remove
   */
  void removeTodoList(String name);

  /**
   * Renames the TodoList to the newName.
   *
   * @param oldName the name of the TodoList to rename
   * @param newName the new name
   */
  void renameTodoList(String oldName, String newName);

  /**
   * Notifies that the TodoList has changed, e.g. TodoItems
   * have been mutated, added or removed.
   *
   * @param todoList the TodoList that has changed
   */
  void notifyTodoListChanged(AbstractTodoList todoList);
}