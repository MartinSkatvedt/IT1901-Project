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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import calendar.core.Event;

import calendar.core.User;
import com.fasterxml.jackson.databind.node.ArrayNode;

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
            ArrayNode events = (ArrayNode) calendarNode.get("events");

            for (JsonNode event : events) {
                Event e = new Event("jsonHeader", "descriptionHeader", LocalDate.of(2021, 10, 10), "00:00");
                JsonNode headerNode = event.get("header");
                if (headerNode instanceof TextNode) {
                    e.setHeader(headerNode.asText());
                }

                JsonNode descriptionNode = event.get("description");
                if (descriptionNode instanceof TextNode) {
                    e.setDescription(descriptionNode.asText());
                }

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                JsonNode dateNode = event.get("date");
                if (dateNode instanceof TextNode) {
                    e.setDate(LocalDate.parse(dateNode.asText(), formatter));
                }

                JsonNode timeNode = event.get("time");
                if (timeNode instanceof TextNode) {
                    e.setTime(timeNode.asText());
                }

                item.getCalendar().addEvent(e);
            }
        }

        return item;
    }
}
