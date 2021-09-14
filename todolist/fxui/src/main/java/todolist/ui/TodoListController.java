package todolist.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import todolist.core.TodoItem;
import todolist.core.TodoList;
import todolist.core.TodoListListener;

public class TodoListController {

  @FXML
  Button newTodoItemButton;

  @FXML
  TextField newTodoItemText;

  @FXML
  ListView<TodoItem> todoItemsView;

  @FXML
  Button deleteTodoItemButton;

  private Collection<Button> selectionButtons;

  private TodoList todoList = null;

  public TodoList getTodoList() {
    return todoList;
  }

  /**
   * Sets the TodoList managed by this controller.
   * The corresponding views will be updated.
   *
   * @param todoList the TodoList
   */
  public void setTodoList(TodoList todoList) {
    if (this.todoList != null) {
      this.todoList.removeTodoListListener(todoListListener);
    }
    this.todoList = todoList;
    dragHandler.setTodoList(todoList);
    updateView();
    if (this.todoList != null) {
      this.todoList.addTodoListListener(todoListListener);
    }
  }

  private Callback<TodoList, Void> onTodoListChanged = null;

  public void setOnTodoListChanged(Callback<TodoList, Void> onTodoListChanged) {
    this.onTodoListChanged = onTodoListChanged;
  }

  private TodoListListener todoListListener = todoList -> {
    if (onTodoListChanged != null) {
      onTodoListChanged.call(getTodoList());
    }
    updateView();
  };

  private TodoItemListCellDragHandler dragHandler;

  @FXML
  void initialize() {
    selectionButtons = List.of(deleteTodoItemButton);
    dragHandler = new TodoItemListCellDragHandler();
    todoItemsView.setCellFactory(listView -> {
      TodoItemListCell listCell = new TodoItemListCell();
      dragHandler.registerHandlers(listCell);
      return listCell;
    });
    todoItemsView.getSelectionModel().selectedItemProperty()
        .addListener((prop, oldValue, newValue) -> updateTodoListButtons());
    todoItemsView.setEditable(true);
  }

  protected void updateView() {
    List<TodoItem> items = new ArrayList<>();
    if (todoList != null) {
      items.addAll(getTodoList().getUncheckedTodoItems());
      items.addAll(getTodoList().getCheckedTodoItems());
    }
    TodoItem selectedItem = todoItemsView.getSelectionModel().getSelectedItem();
    todoItemsView.getItems().setAll(items);
    // keep selection
    if (selectedItem != null) {
      todoItemsView.getSelectionModel().select(selectedItem);
    }
    newTodoItemText.setText(null);
    newTodoItemButton.setDisable(todoList == null);
    newTodoItemText.setDisable(todoList == null);
    updateTodoListButtons();
  }

  private void updateTodoListButtons() {
    boolean disable = todoItemsView.getSelectionModel().getSelectedItem() == null;
    for (Button button : selectionButtons) {
      button.setDisable(disable);
    }
    // TODO in progress...
    getRowLayoutY(todoItemsView, listCell -> isSelected(todoItemsView, listCell), 0);
    // System.out.println(rowLayoutY);
  }

  private boolean isSelected(ListView<?> listView, ListCell<?> listCell) {
    return isSelected(listView, listCell.getItem());
  }

  private boolean isSelected(ListView<?> listView, Object item) {
    return todoItemsView.getSelectionModel().getSelectedItems().contains(item);
  }

  @SuppressWarnings("unchecked")
  private <T> double getRowLayoutY(ListView<T> listView, Predicate<ListCell<T>> test, int num) {
    for (Node child : listView.lookupAll(".list-cell")) {
      if (child instanceof ListCell) {
        ListCell<T> listCell = (ListCell<T>) child;
        if (test.test(listCell) && num-- == 0) {
          double dy = 0;
          Node node = listCell;
          while (node != todoItemsView) {
            dy += node.getLayoutY();
            node = node.getParent();
          }
          return dy;
        }
      }
    }
    return -1;
  }

  @FXML
  void handleNewTodoItemAction() {
    TodoItem item = getTodoList().createTodoItem();
    item.setText(newTodoItemText.getText());
    getTodoList().addTodoItem(item);
    todoItemsView.getSelectionModel().select(item);
  }

  @FXML
  void handleDeleteItemAction() {
    int index = todoItemsView.getSelectionModel().getSelectedIndex();
    TodoItem item = todoItemsView.getItems().get(index);
    if (item != null) {
      getTodoList().removeTodoItem(item);
      selectWithinBounds(index);
    }
  }

  private int selectWithinBounds(int index) {
    int maxIndex = todoItemsView.getItems().size() - 1;
    if (index > maxIndex) {
      index = maxIndex;
    }
    if (index >= 0) {
      todoItemsView.getSelectionModel().select(index);
      return index;
    }
    return -1;
  }

  @FXML
  void handleCheckItemAction() {
    TodoItem item = todoItemsView.getSelectionModel().getSelectedItem();
    if (item != null) {
      // toggle checked flag
      item.setChecked(! item.isChecked());
    }
  }
}
