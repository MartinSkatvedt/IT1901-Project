package calendar.json;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.core.Version;
import calendar.core.Calendar;

@SuppressWarnings("serial")
public class CalendarModule extends SimpleModule {
    private static final String NAME = "CalendarModule";

    /**
     * Initializes this UserModule with appropriate serializers and deserializers.
     */
    public CalendarModule(boolean deepTodoModelSerializer) {
      super(NAME, Version.unknownVersion());
      addDeserializer(Calendar.class, new CalendarDeserializer());
      addSerializer(Calendar.class, new CalendarSerializer());
    }

    public CalendarModule() {
      this(true);
    }
    
}
