package todolist.json;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import todolist.core.AbstractTodoList;
import todolist.core.TodoItem;
import todolist.core.TodoModel;

/**
 * A Jackson module for configuring JSON serialization of TodoModel instances.
 */
@SuppressWarnings("serial")
public class TodoModule extends SimpleModule {

  private static final String NAME = "TodoModule";

  /**
   * Initializes this TodoModule with appropriate serializers and deserializers.
   */
  public TodoModule(boolean deepTodoModelSerializer) {
    super(NAME, Version.unknownVersion());
    addSerializer(TodoItem.class, new TodoItemSerializer());
    addSerializer(AbstractTodoList.class, new TodoListSerializer());
    addSerializer(TodoModel.class, new TodoModelSerializer(deepTodoModelSerializer));
    addDeserializer(TodoItem.class, new TodoItemDeserializer());
    addDeserializer(AbstractTodoList.class, new TodoListDeserializer());
    addDeserializer(TodoModel.class, new TodoModelDeserializer());
  }

  public TodoModule() {
    this(true);
  }
}
