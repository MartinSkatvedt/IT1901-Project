package calendar.json.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import calendar.core.User;

public class UserSerializer extends JsonSerializer<User> {
    @Override
    public void serialize(User user, JsonGenerator jsonGen, SerializerProvider serializerProvider)
        throws IOException {
            CalendarSerializer calendarSerializer = new CalendarSerializer();
            jsonGen.writeStartObject();
            jsonGen.writeStringField("username", user.getUsername());
            
            jsonGen.writeObjectFieldStart("calendar");
            calendarSerializer.serialize(user.getCalendar(), jsonGen, serializerProvider);
            jsonGen.writeEndObject();
            jsonGen.writeEndObject();
    }
}
