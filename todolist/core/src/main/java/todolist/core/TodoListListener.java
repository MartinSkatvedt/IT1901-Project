package todolist.core;

/**
 * Listener interface for changes to a TodoList.
 */
public interface TodoListListener {
  /**
   * Notifies that the given TodoList has changed.
   *
   * @param list the changed TodoList
   */
  public void todoListChanged(TodoList list);
}
