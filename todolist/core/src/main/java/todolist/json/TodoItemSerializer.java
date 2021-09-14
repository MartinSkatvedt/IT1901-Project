package todolist.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import todolist.core.TodoItem;

class TodoItemSerializer extends JsonSerializer<TodoItem> {

  /*
   * format: { "text": "...", "checked": false, "deadline": ... }
   */

  @Override
  public void serialize(TodoItem item, JsonGenerator jsonGen, SerializerProvider serializerProvider)
      throws IOException {
    jsonGen.writeStartObject();
    jsonGen.writeStringField("text", item.getText());
    jsonGen.writeBooleanField("checked", item.isChecked());
    if (item.getDeadline() != null) {
      jsonGen.writeStringField("deadline", item.getDeadline().toString());
    }
    jsonGen.writeEndObject();
  }
}
