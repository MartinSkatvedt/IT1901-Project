package todolist.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import todolist.core.AbstractTodoList;
import todolist.core.TodoItem;
import todolist.core.TodoList;

class TodoListSerializer extends JsonSerializer<AbstractTodoList> {

  /*
   * format: { "name": "...", "items": [ ... ] }
   */

  @Override
  public void serialize(AbstractTodoList list, JsonGenerator jsonGen,
      SerializerProvider serializerProvider) throws IOException {
    jsonGen.writeStartObject();
    if (list.getName() != null) {
      jsonGen.writeStringField("name", list.getName());
    }
    if (list.getDeadline() != null) {
      jsonGen.writeStringField("deadline", list.getDeadline().toString());
    }
    if (list instanceof TodoList) {
      jsonGen.writeArrayFieldStart("items");
      for (TodoItem item : list) {
        jsonGen.writeObject(item);
      }
      jsonGen.writeEndArray();
    }
    jsonGen.writeEndObject();
  }
}
