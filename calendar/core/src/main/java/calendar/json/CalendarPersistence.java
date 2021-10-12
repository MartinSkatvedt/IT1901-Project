package calendar.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import calendar.json.CalendarModule;
import calendar.core.Calendar;

public class CalendarPersistence {
    private ObjectMapper mapper;

  public CalendarPersistence() {
    mapper = new ObjectMapper();
    mapper.registerModule(new CalendarModule());
  }

  public Calendar readCalendar(Reader reader) throws IOException {
    return mapper.readValue(reader, Calendar.class);
  }

  public void writeCalendar(Calendar calendar, Writer writer) throws IOException {
    mapper.writerWithDefaultPrettyPrinter().writeValue(writer, calendar);
  }

  private Path saveFilePath = null;

  public void setSaveFile(String saveFile) {
    this.saveFilePath = Paths.get(System.getProperty("user.home"), saveFile);
  }

  /**
   * Loads a calendar from the saved file (saveFilePath) in the calendar.home folder.
   *
   * @return the loaded calendar
   */
  public Calendar loadCalendar() throws IOException, IllegalStateException {
    if (saveFilePath == null) {
      throw new IllegalStateException("Save file path is not set, yet");
    }
    try (Reader reader = new FileReader(saveFilePath.toFile(), StandardCharsets.UTF_8)) {
      return readCalendar(reader);
    }
  }

  /**
   * Saves a calendar to the saveFilePath in the calendar.home folder.
   *
   * @param calendar the calendar to save
   */
  public void saveCalendar(Calendar calendar) throws IOException, IllegalStateException {
    if (saveFilePath == null) {
      throw new IllegalStateException("Save file path is not set, yet");
    }
    try (Writer writer = new FileWriter(saveFilePath.toFile(), StandardCharsets.UTF_8)) {
      writeCalendar(calendar, writer);
    }
  }
}