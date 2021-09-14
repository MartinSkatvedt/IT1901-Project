package todolist.core;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

/**
 * A non-complete, but concrete class for todo lists without items.
 * Can be used as placeholder for real TodoList instances.
 */
public class AbstractTodoList implements Iterable<TodoItem> {

  private String name;

  private LocalDateTime deadline;

  public AbstractTodoList(String name) {
    setName(name);
  }

  @Override
  public String toString() {
    return String.format("[AbstractTodoList name=%s deadline=%s]", getName(), getDeadline());
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public LocalDateTime getDeadline() {
    return deadline;
  }

  public void setDeadline(LocalDateTime deadline) {
    this.deadline = deadline;
  }

  public TodoItem createTodoItem() {
    throw new UnsupportedOperationException("An abstract TodoList cannot create TodoItems");
  }

  /**
   * Adds the provided TodoItems to this TodoList.
   * If a TodoItem is not an instance of TodoListItem,
   * its contents is copied in to a new TodoListItem and that is added instead.
   *
   * @param items the TodoItems to add
   */
  public void addTodoItems(TodoItem... items) {
    throw new UnsupportedOperationException("An abstract TodoList cannot add TodoItems");
  }

  /**
   * Adds the provided TodoItem to this TodoList.
   * If the TodoItem is not an instance of TodoListItem,
   * its contents is copied in to a new TodoListItem and that is added instead.
   *
   * @param item the TodoItem to add
   */
  public void addTodoItem(TodoItem item) {
    addTodoItems(item);
  }

  public void removeTodoItem(TodoItem item) {
    throw new UnsupportedOperationException("An abstract TodoList cannot remove TodoItems");
  }

  @Override
  public Iterator<TodoItem> iterator() {
    return Collections.emptyIterator();
  }

  protected Collection<TodoItem> getTodoItems(Boolean checked) {
    return Collections.emptyList();
  }

  public Collection<TodoItem> getTodoItems() {
    return getTodoItems(null);
  }

  public Collection<TodoItem> getCheckedTodoItems() {
    return getTodoItems(true);
  }

  public Collection<TodoItem> getUncheckedTodoItems() {
    return getTodoItems(false);
  }

  // methods related to deadlines

  public boolean isOverdue() {
    return deadline != null && deadline.isBefore(LocalDateTime.now())
        && (!getUncheckedTodoItems().isEmpty());
  }

  public Collection<TodoItem> getOverdueTodoItems() {
    return Collections.emptyList();
  }

  // index-oriented methods

  public int indexOf(TodoItem item) {
    return -1;
  }

  /**
   * Moves the provided TodoItem to a new position given by newIndex.
   * Items in-betweem the old and new positions are shifted.
   *
   * @param item     the item to move
   * @param newIndex the new position
   */
  public void moveTodoItem(TodoItem item, int newIndex) {
    throw new UnsupportedOperationException("An abstract TodoList cannot move TodoItems");
  }

  // støtte for lytting

  private Collection<TodoListListener> todoListListeners = new ArrayList<>();

  public void addTodoListListener(TodoListListener listener) {
    todoListListeners.add(listener);
  }

  public void removeTodoListListener(TodoListListener listener) {
    todoListListeners.remove(listener);
  }

  protected void fireTodoListChanged(TodoItem item) {
    fireTodoListChanged();
  }

  protected void fireTodoListChanged() {
    for (TodoListListener listener : todoListListeners) {
      fireTodoListChanged(listener);
    }
  }

  protected void fireTodoListChanged(TodoListListener listener) {
  }
}
