package todolist.core;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The root container of Todo-related data.
 */
public class TodoModel implements Iterable<AbstractTodoList> {

  private Map<String, AbstractTodoList> todoLists = new LinkedHashMap<>();

  @Override
  public String toString() {
    return String.format("[TodoModel #todoLists=%s]", todoLists.size());
  }

  /**
   * Checks if this TodoModel already has a TodoList with the provided name.
   *
   * @param name the name to check
   * @return true if a TodoList with the provided name exists, false otherwise
   */
  public boolean hasTodoList(String name) {
    return todoLists.containsKey(name);
  }

  /**
   * Checks if it is OK to add a list with the provided name.
   *
   * @param name the new name
   * @return true if the name is value, false otherwise
   */
  public boolean isValidTodoListName(String name) {
    return name.strip().length() > 0;
  }

  /**
   * Adds the TodoList to this TodoModel.
   *
   * @param list the TodoList
   * @throws IllegalArgumentException if the list's name is invalid
   */
  public void addTodoList(AbstractTodoList list) {
    if (! isValidTodoListName(list.getName())) {
      throw new IllegalArgumentException(list.getName() + " is not a legal name for a new list");
    }
    todoLists.put(list.getName(), list);
  }

  public void removeTodoList(AbstractTodoList list) {
    todoLists.remove(list.getName());
  }

  @Override
  public Iterator<AbstractTodoList> iterator() {
    return todoLists.values().iterator();
  }

  /**
   * Gets the TodoList with the provided name.
   *
   * @param name the name
   * @return the TodoList with the provided name
   */
  public AbstractTodoList getTodoList(String name) {
    return todoLists.get(name);
  }

  /**
   * Replaces an existing TodoList with the same name, or adds it.
   *
   * @param todoList the TodoList
   * @return the replaced TodoList, or null
   */
  public AbstractTodoList putTodoList(AbstractTodoList todoList) {
    return todoLists.put(todoList.getName(), todoList);
  }
}
