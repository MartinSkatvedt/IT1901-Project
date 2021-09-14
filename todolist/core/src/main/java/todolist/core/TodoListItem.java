package todolist.core;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * TodoItems that belong to a TodoList.
 */
public class TodoListItem extends TodoItem {

  private final TodoList todoList;

  public TodoListItem(TodoList todoList) {
    this.todoList = todoList;
  }

  public TodoList getTodoList() {
    return todoList;
  }

  @Override
  public void setText(String text) {
    if (! Objects.equals(text, getText())) {
      super.setText(text);
      todoList.fireTodoListChanged(this);
    }
  }

  @Override
  public void setChecked(boolean checked) {
    if (checked != isChecked()) {
      super.setChecked(checked);
      todoList.fireTodoListChanged(this);
    }
  }

  @Override
  public void setDeadline(LocalDateTime deadline) {
    if (! Objects.equals(deadline, getDeadline())) {
      super.setDeadline(deadline);
      todoList.fireTodoListChanged(this);
    }
  }

  @Override
  public void setAs(TodoItem other) {
    boolean equals = isChecked() == other.isChecked()
        && Objects.equals(getText(), other.getText())
        && Objects.equals(getDeadline(), other.getDeadline());
    if (! equals) {
      super.setAs(other);
      todoList.fireTodoListChanged(this);
    }
  }
}
