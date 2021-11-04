package calendar.json.util;

import calendar.core.User;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;


public class UserSerializer extends JsonSerializer<User> {
    @Override
    public void serialize(User user, JsonGenerator jsonGen, SerializerProvider serializerProvider) throws IOException {
        jsonGen.writeStartObject();
        jsonGen.writeStringField("username", user.getUsername());
        jsonGen.writeObjectFieldStart("calendar");
        CalendarSerializer calendarSerializer = new CalendarSerializer();
        calendarSerializer.serialize(user.getCalendar(), jsonGen, serializerProvider);
        jsonGen.writeEndObject();
        jsonGen.writeEndObject();
    }
}
