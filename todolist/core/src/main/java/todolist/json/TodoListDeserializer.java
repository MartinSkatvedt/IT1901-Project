package todolist.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import java.io.IOException;
import java.time.LocalDateTime;
import todolist.core.AbstractTodoList;
import todolist.core.TodoItem;
import todolist.core.TodoList;

class TodoListDeserializer extends JsonDeserializer<AbstractTodoList> {

  private TodoItemDeserializer todoItemDeserializer = new TodoItemDeserializer();
  /*
   * format: { "items": [ ... ] }
   */

  @Override
  public AbstractTodoList deserialize(JsonParser parser, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    TreeNode treeNode = parser.getCodec().readTree(parser);
    return deserialize((JsonNode) treeNode);
  }

  AbstractTodoList deserialize(JsonNode treeNode) {
    if (treeNode instanceof ObjectNode objectNode) {
      JsonNode nameNode = objectNode.get("name");
      if (! (nameNode instanceof TextNode)) {
        return null;
      }
      String name = nameNode.asText();
      JsonNode itemsNode = objectNode.get("items");
      boolean hasItems = itemsNode instanceof ArrayNode;
      AbstractTodoList todoList = (hasItems ? new TodoList(name) : new AbstractTodoList(name));
      JsonNode deadlineNode = objectNode.get("deadline");
      if (deadlineNode instanceof TextNode) {
        todoList.setDeadline(LocalDateTime.parse(deadlineNode.asText()));
      }
      if (hasItems) {
        for (JsonNode elementNode : ((ArrayNode) itemsNode)) {
          TodoItem item = todoItemDeserializer.deserialize(elementNode);
          if (item != null) {
            todoList.addTodoItem(item);
          }
        }
      }
      return todoList;
    }
    return null;
  }   
}