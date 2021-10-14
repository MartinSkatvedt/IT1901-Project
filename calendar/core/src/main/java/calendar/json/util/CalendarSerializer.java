package calendar.json.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import calendar.core.Calendar;
import calendar.core.Event;

public class CalendarSerializer extends JsonSerializer<Calendar> {

    @Override
    public void serialize(Calendar calendar, JsonGenerator jsonGen, SerializerProvider serializerProvider) throws IOException {
      EventSerializer eventSerializer = new EventSerializer();
      jsonGen.writeArrayFieldStart("events");
      for (Event event : calendar.getEvents()) {
        eventSerializer.serialize(event, jsonGen, serializerProvider);
      }
      jsonGen.writeEndArray();
    }
}