package calendar.json;

import com.fasterxml.jackson.databind.module.SimpleModule;
import calendar.core.User;
import calendar.json.util.UserDeserializer;
import calendar.json.util.UserSerializer;
import com.fasterxml.jackson.core.Version;


@SuppressWarnings("serial")
public class UserModule extends SimpleModule {
    
    private static final String NAME = "UserModule";

    /**
     * Initializes this UserModule with serializers and deserializers.
     */
    public UserModule(boolean deepTodoModelSerializer) {
      super(NAME, Version.unknownVersion());
      addSerializer(User.class, new UserSerializer());
      addDeserializer(User.class, new UserDeserializer());
    }

    public UserModule() {
      this(true);
    }
}
