package todolist.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import todolist.core.TodoModel;

/**
 * Wrapper class for JSON serialization,
 * to avoid direct compile dependencies on Jackson for other modules.
 */
public class TodoPersistence {

  private ObjectMapper mapper;

  public TodoPersistence() {
    mapper = new ObjectMapper();
    mapper.registerModule(new TodoModule());
  }

  public TodoModel readTodoModel(Reader reader) throws IOException {
    return mapper.readValue(reader, TodoModel.class);
  }

  public void writeTodoModel(TodoModel todoModel, Writer writer) throws IOException {
    mapper.writerWithDefaultPrettyPrinter().writeValue(writer, todoModel);
  }

  private Path saveFilePath = null;

  public void setSaveFile(String saveFile) {
    this.saveFilePath = Paths.get(System.getProperty("user.home"), saveFile);
  }

  /**
   * Loads a TodoModel from the saved file (saveFilePath) in the user.home folder.
   *
   * @return the loaded TodoModel
   */
  public TodoModel loadTodoModel() throws IOException, IllegalStateException {
    if (saveFilePath == null) {
      throw new IllegalStateException("Save file path is not set, yet");
    }
    try (Reader reader = new FileReader(saveFilePath.toFile(), StandardCharsets.UTF_8)) {
      return readTodoModel(reader);
    }
  }

  /**
   * Saves a TodoModel to the saveFilePath in the user.home folder.
   *
   * @param todoModel the TodoModel to save
   */
  public void saveTodoModel(TodoModel todoModel) throws IOException, IllegalStateException {
    if (saveFilePath == null) {
      throw new IllegalStateException("Save file path is not set, yet");
    }
    try (Writer writer = new FileWriter(saveFilePath.toFile(), StandardCharsets.UTF_8)) {
      writeTodoModel(todoModel, writer);
    }
  }
}
