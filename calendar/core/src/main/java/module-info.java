module calendar.core {
    requires transitive com.fasterxml.jackson.databind;

    exports calendar.core;
    exports calendar.json;
    exports calendar.json.util;

    opens calendar.core;
}
