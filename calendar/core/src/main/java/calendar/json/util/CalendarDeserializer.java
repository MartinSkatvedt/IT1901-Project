package calendar.json.util;

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
import java.time.LocalDate;

public class CalendarDeserializer extends JsonDeserializer<Calendar> {
  @Override
  public Calendar deserialize(JsonParser parser, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    TreeNode treeNode = parser.getCodec().readTree(parser);
    return deserialize((JsonNode) treeNode);
  }

  Calendar deserialize(JsonNode jsonNode) {
    Calendar item = new Calendar();
    ArrayNode events = (ArrayNode) jsonNode.get("events");

    for (JsonNode event : events) {
      Event e = new Event("jsonHeader", "descriptionHeader", LocalDate.of(2021, 10, 10), "00:00");
      JsonNode headerNode = event.get("header");
      if (headerNode instanceof TextNode) {
        e.setHeader(headerNode.asText());
      }
      JsonNode descriptionNode = jsonNode.get("description");
      if (descriptionNode instanceof TextNode) {
        e.setDescription(descriptionNode.asText());
      }
      item.addEvent(e);
    }
    return item;
  }
}
