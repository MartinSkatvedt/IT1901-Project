package todolist.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.Iterator;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import todolist.core.AbstractTodoList;
import todolist.core.TodoItem;
import todolist.core.TodoList;
import todolist.core.TodoModel;

public class TodoModuleTest {

  // {"lists":[{"name": "todo", "items":[{"text":"item1","checked":false},{"text":"item2","checked":true}]}]}

  private static ObjectMapper mapper;

  @BeforeAll
  public static void setUp() {
    mapper = new ObjectMapper();
    mapper.registerModule(new TodoModule());
  }

  private final static String todoListWithTwoItems = """
    {
      "lists": [
        {
          "name": "todo",
          "items": [
            {
              "text": "item1",
              "checked": false
            },
            {
              "text": "item2",
              "checked": true,
              "deadline": "2020-10-01T14:53:11"
            }
          ]
        }
      ]
    }
    """;

  @Test
  public void testSerializers() {
    TodoModel model = new TodoModel();
    TodoList list = new TodoList("todo");
    model.addTodoList(list);
    TodoItem item1 = list.createTodoItem();
    item1.setText("item1");
    TodoItem item2 = list.createTodoItem();
    item2.setText("item2");
    item2.setChecked(true);
    item2.setDeadline(LocalDateTime.parse("2020-10-01T14:53:11"));
    list.addTodoItem(item1);
    list.addTodoItem(item2);
    try {
      assertEquals(todoListWithTwoItems.replaceAll("\\s+", ""), mapper.writeValueAsString(model));
    } catch (JsonProcessingException e) {
      fail();
    }
  }

  static void checkTodoItem(TodoItem item, String text, boolean checked, LocalDateTime deadline) {
    assertEquals(text, item.getText());
    assertTrue(checked == item.isChecked());
    assertEquals(deadline, item.getDeadline());
  }

  static void checkTodoItem(TodoItem item1, TodoItem item2) {
    checkTodoItem(item1, item2.getText(), item2.isChecked(), item2.getDeadline());
  }

  @Test
  public void testDeserializers() {
    try {
      TodoModel model = mapper.readValue(todoListWithTwoItems, TodoModel.class);
      assertTrue(model.iterator().hasNext());
      AbstractTodoList list = model.iterator().next();
      assertEquals("todo", list.getName());
      Iterator<TodoItem> it = list.iterator();
      assertTrue(it.hasNext());
      checkTodoItem(it.next(), "item1", false, null);
      assertTrue(it.hasNext());
      checkTodoItem(it.next(), "item2", true, LocalDateTime.parse("2020-10-01T14:53:11"));
      assertFalse(it.hasNext());
    } catch (JsonProcessingException e) {
      fail();
    }
  }

  @Test
  public void testSerializersDeserializers() {
    TodoModel model = new TodoModel();
    TodoList list = new TodoList("todo");
    model.addTodoList(list);
    TodoItem item1 = new TodoItem();
    item1.setText("item1");
    TodoItem item2 = new TodoItem();
    item2.setText("item2");
    item2.setChecked(true);
    item2.setDeadline(LocalDateTime.parse("2020-10-01T14:53:11"));
    list.addTodoItem(item1);
    list.addTodoItem(item2);
    try {
      String json = mapper.writeValueAsString(model);
      TodoModel model2 = mapper.readValue(json, TodoModel.class);
      assertTrue(model2.iterator().hasNext());
      AbstractTodoList list2 = model.iterator().next();
      assertEquals("todo", list.getName());
      Iterator<TodoItem> it = list2.iterator();
      assertTrue(it.hasNext());
      checkTodoItem(it.next(), item1);
      assertTrue(it.hasNext());
      checkTodoItem(it.next(), item2);
      assertFalse(it.hasNext());
    } catch (JsonProcessingException e) {
      fail();
    }
  }
}
