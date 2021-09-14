package todolist.ui;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import todolist.core.AbstractTodoList;
import todolist.core.TodoList;

public class TodoModelController {

  private TodoModelAccess todoModelAccess;

  @FXML
  String userTodoListPath;

  @FXML
  String sampleTodoListResource;

  @FXML
  ComboBox<String> todoListsView;

  @FXML
  TodoListController todoListViewController;

  public void setTodoModelAccess(TodoModelAccess todoModelAccess) {
    this.todoModelAccess = todoModelAccess;
    updateTodoListsView(null);
  }

  @FXML
  void initialize() {
    // kobler data til list-controll
    initializeTodoListsView();
    todoListViewController.setOnTodoListChanged(todoList -> {
      todoModelAccess.notifyTodoListChanged(todoList);
      return null;
    });
  }

  private String addNewTodoListText = "<add new todo list>";

  private void initializeTodoListsView() {
    todoListsView.setEditable(true);
    todoListsView.valueProperty().addListener((prop, oldName, newName) -> {
      // System.out.println("valueProperty: -> "
      //    + todoListsView.getSelectionModel().getSelectedIndex() + " -> "
      //    + (oldName != null ? ("\"" + oldName + "\"") : null) + " -> " 
      //    + (newName != null ? ("\"" + newName + "\"") : null));
      if (newName != null && (! todoModelAccess.isValidTodoListName(newName))) {
        // allow user to edit name
      } else if (oldName != null && newName != null
            && (! todoListsView.getItems().contains(newName))) {
        // either new name of dummy item or existing item
        if (addNewTodoListText.equals(oldName)) {
          // add as new list
          todoModelAccess.addTodoList(new TodoList(newName));
          updateTodoListsView(newName);
        } else {
          // update name
          todoModelAccess.renameTodoList(oldName, newName);
          updateTodoListsView(newName);
        }
      } else if (todoListsView.getSelectionModel().getSelectedIndex() == 0) {
        // run later to avoid conflicts with event processing
        Platform.runLater(() -> {
          todoListsView.getEditor().selectAll();
        });
      } else if (todoListsView.getSelectionModel().getSelectedIndex() >= 0) {
        AbstractTodoList todoList = getSelectedTodoList();
        if (! (todoList instanceof TodoList)) {
          // retrieve actual list
          todoList = todoModelAccess.getTodoList(todoList.getName());
        }
        todoListViewController.setTodoList(todoList instanceof TodoList ? (TodoList) todoList : null);
      }
    });
  }

  AbstractTodoList getSelectedTodoList() {
    return todoModelAccess.getTodoList(todoListsView.getSelectionModel().getSelectedItem());
  }

  protected void updateTodoListsView(String newSelection) {
    List<String> items = new ArrayList<>();
    // dummy element used for creating new ones, with null name
    items.add(addNewTodoListText);
    items.addAll(todoModelAccess.getTodoListNames());
    todoListsView.getItems().setAll(items);
    if (newSelection != null) {
      todoListsView.setValue(newSelection);
    } else {
      todoListsView.getSelectionModel().select(todoListsView.getItems().size() > 1 ? 1 : 0);
    }
  }
}
