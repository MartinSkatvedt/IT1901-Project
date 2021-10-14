package calendar.json.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import calendar.core.Event;


public class EventSerializer extends JsonSerializer<Event> {

    @Override
    public void serialize(Event event, JsonGenerator jsonGen, SerializerProvider serializerProvider)
        throws IOException {
      jsonGen.writeStartObject();
      jsonGen.writeStringField("header", event.getHeader());
      jsonGen.writeStringField("description", event.getDescription());
      jsonGen.writeEndObject();
    }
}
