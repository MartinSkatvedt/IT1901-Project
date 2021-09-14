package todolist.ui;

import java.util.Collection;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import todolist.core.TodoItem;

public class TodoItemListCell extends ListCell<TodoItem> {

  // for whole row
  private HBox todoItemControl = null;
  // view and editor
  private CheckBox checkedView = null;
  // view
  private Label textView = null;
  // editor
  private TextField textEditor = null;

  @Override
  protected void updateItem(TodoItem item, boolean empty) {
    super.updateItem(item, empty);
    setText(null);
    if (empty || item == null) {
      setGraphic(null);
    } else {
      // ensure we have created controls common for view and editor
      if (todoItemControl == null) {
        todoItemControl = new HBox();
        checkedView = new CheckBox();
        checkedView.selectedProperty().addListener((prop, oldValue, newValue) -> {
          getItem().setAs(new TodoItem()
              .checked(checkedView.isSelected())
              // use editor text if it's active, otherwise existing text
              .text(isEditing() ? textEditor.getText() : getItem().getText())
          );
        });
        todoItemControl.getChildren().add(checkedView);
      }
      // choose between editor and view
      if (isEditing()) {
        configureEditor();
      } else {
        configureViewer();
      }
      setGraphic(todoItemControl);
    }
  }
  
  // helper method
  private void useSpecificChild(Control use, Control useNot) {
    Collection<Node> children = todoItemControl.getChildren();
    children.remove(useNot);
    if (! children.contains(use)) {
      todoItemControl.getChildren().add(use);
    }
  }

  private void configureViewer() {
    if (textView == null) {
      textView = new Label();
    }
    useSpecificChild(textView, textEditor);
    checkedView.setSelected(getItem().isChecked());
    textView.setText(getItem().getText());
  }

  private void configureEditor() {
    if (textEditor == null) {
      textEditor = new TextField();
      textEditor.setOnAction(event -> {
        commitEdit(getItem());
      });
    }
    useSpecificChild(textEditor, textView);
    checkedView.setSelected(getItem().isChecked());
    textEditor.setText(getItem().getText());
  }

  @Override
  public void startEdit() {
    super.startEdit();
    if (isEditing()) {
      configureEditor();
    }
  }

  @Override
  public void commitEdit(TodoItem item) {
    super.commitEdit(item);
    getItem().setText(textEditor.getText());
  }

  @Override
  public void cancelEdit() {
    super.cancelEdit();
    configureViewer();
  }
}
