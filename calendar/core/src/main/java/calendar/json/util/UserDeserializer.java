package calendar.json.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import java.io.IOException;
import calendar.core.User;

public class UserDeserializer extends JsonDeserializer<User> {
    
    @Override
    public User deserialize(JsonParser parser, DeserializationContext ctxt)
        throws IOException, JsonProcessingException {
      TreeNode treeNode = parser.getCodec().readTree(parser);
      return deserialize((JsonNode) treeNode);
    }
  
    User deserialize(JsonNode jsonNode) {
        
        
        User item = new User("init");

        JsonNode usernameNode = jsonNode.get("username");
        if (usernameNode instanceof TextNode) {
            item = new User(usernameNode.asText());
        }
        JsonNode calendarNode = jsonNode.get("calendar");
        if (calendarNode instanceof ObjectNode) {
            System.out.println(calendarNode);
        }
        
        return item;
    } 
}
