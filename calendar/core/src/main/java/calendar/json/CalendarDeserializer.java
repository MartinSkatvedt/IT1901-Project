package calendar.json;
import java.util.GregorianCalendar;
import calendar.core.Calendar;
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
import calendar.core.Event;

public class CalendarDeserializer extends JsonDeserializer<Calendar> {
    @Override
    public Calendar deserialize(JsonParser parser, DeserializationContext ctxt)
        throws IOException, JsonProcessingException {
      TreeNode treeNode = parser.getCodec().readTree(parser);
      return deserialize((JsonNode) treeNode);
    }
  
    Calendar deserialize(JsonNode jsonNode) {
      if (jsonNode instanceof ObjectNode objectNode) {
        Calendar item = new Calendar();
        ArrayNode events = (ArrayNode)objectNode.get("events");

        for (JsonNode event: events) {
          Event e = new Event("jsonHeader", "descriptionHeader", new GregorianCalendar());
          JsonNode headerNode = event.get("header");
          if (headerNode instanceof TextNode) {
            e.setHeader(headerNode.asText());
          }
          JsonNode descriptionNode = objectNode.get("description");
          if (descriptionNode instanceof TextNode) {
            e.setDescription(descriptionNode.asText());
          }
          item.addEvent(e);
        }
        return item;
      }
      return null;
    } 
}
