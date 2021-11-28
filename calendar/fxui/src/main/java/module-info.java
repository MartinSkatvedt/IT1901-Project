module calendar.ui {
    requires transitive com.fasterxml.jackson.databind;
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires calendar.core;

    opens calendar.ui to javafx.graphics, javafx.fxml;
}
