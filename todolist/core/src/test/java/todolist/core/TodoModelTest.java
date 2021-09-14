package todolist.core;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Iterator;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class TodoModelTest {

  private TodoModel todoModel;
  private TodoList list1, list2;

  @BeforeEach
  public void setUp() {
    todoModel = new TodoModel();
    list1 = new TodoList("todo1");
    list2 = new TodoList("todo2");
  }

  private void checkTodoLists(TodoList... lists) {
    Iterator<AbstractTodoList> it = todoModel.iterator();
    int pos = 0;
    while (it.hasNext()) {
      assertTrue(pos < lists.length);
      assertSame(lists[pos], it.next());
      pos++;
    }
    assertTrue(pos == lists.length);
  }

  @ParameterizedTest
  @ValueSource(strings = {"", " ", "\t"})
  public void testIsValidTodoListName_invalid(String invalid) {
    assertFalse(todoModel.isValidTodoListName(invalid));
  }

  @Test
  public void testIsValidTodoListName() {
    assertTrue(todoModel.isValidTodoListName("list1"));
    assertTrue(todoModel.isValidTodoListName(list1.getName()));
  }

  @Test
  public void testAddTodoList() {
    assertFalse(todoModel.iterator().hasNext());
    todoModel.addTodoList(list1);
    checkTodoLists(list1);
    todoModel.addTodoList(list2);
    checkTodoLists(list1, list2);
  }

  @Test
  public void testPutTodoList() {
    assertFalse(todoModel.iterator().hasNext());
    assertNull(todoModel.putTodoList(list1));
    checkTodoLists(list1);
    assertNull(todoModel.putTodoList(list2));
    checkTodoLists(list1, list2);
    TodoList list12 = new TodoList(list1.getName());
    assertSame(list1, todoModel.putTodoList(list12));
    checkTodoLists(list12, list2);
  }
}
