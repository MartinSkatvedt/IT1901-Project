package todolist.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import todolist.core.AbstractTodoList;
import todolist.core.TodoModel;

class TodoModelDeserializer extends JsonDeserializer<TodoModel> {

  private TodoListDeserializer todoListDeserializer = new TodoListDeserializer();
  /*
   * format: { "lists": [ ... ] }
   */

  @Override
  public TodoModel deserialize(JsonParser parser, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    TreeNode treeNode = parser.getCodec().readTree(parser);
    return deserialize((JsonNode) treeNode);
  }

  TodoModel deserialize(JsonNode treeNode) {
    if (treeNode instanceof ObjectNode objectNode) {
      TodoModel model = new TodoModel();
      JsonNode itemsNode = objectNode.get("lists");
      if (itemsNode instanceof ArrayNode arrayNode) {
        for (JsonNode elementNode : arrayNode) {
          AbstractTodoList list = todoListDeserializer.deserialize(elementNode);
          if (list != null) {
            model.addTodoList(list);
          }
        }
      }
      return model;
    }
    return null;
  }   
}