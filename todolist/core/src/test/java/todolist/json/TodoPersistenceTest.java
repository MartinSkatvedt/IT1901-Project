package todolist.json;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.Iterator;
import org.junit.jupiter.api.Test;
import todolist.core.AbstractTodoList;
import todolist.core.TodoItem;
import todolist.core.TodoList;
import todolist.core.TodoModel;

public class TodoPersistenceTest {

  private TodoPersistence todoPersistence = new TodoPersistence();

  @Test
  public void testSerializersDeserializers() {
    TodoModel model = new TodoModel();
    TodoList list = new TodoList("todo");
    model.addTodoList(list);
    TodoItem item1 = new TodoItem();
    item1.setText("item1");
    list.addTodoItem(item1);
    TodoItem item2 = new TodoItem();
    item2.setText("item2");
    item2.setChecked(true);
    item2.setDeadline(LocalDateTime.parse("2020-10-01T14:53:11"));
    list.addTodoItem(item2);
    try {
      StringWriter writer = new StringWriter();
      todoPersistence.writeTodoModel(model, writer);
      String json = writer.toString();
      TodoModel model2 = todoPersistence.readTodoModel(new StringReader(json));
      assertTrue(model2.iterator().hasNext());
      AbstractTodoList list2 = model.iterator().next();
      assertEquals("todo", list.getName());
      Iterator<TodoItem> it = list2.iterator();
      assertTrue(it.hasNext());
      TodoModuleTest.checkTodoItem(it.next(), item1);
      assertTrue(it.hasNext());
      TodoModuleTest.checkTodoItem(it.next(), item2);
      assertFalse(it.hasNext());
    } catch (IOException e) {
      fail();
    }
  }
}