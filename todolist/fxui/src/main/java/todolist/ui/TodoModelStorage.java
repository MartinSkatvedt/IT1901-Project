package todolist.ui;

import fxutil.doc.AbstractDocumentStorage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import todolist.core.TodoModel;
import todolist.json.TodoPersistence;

public class TodoModelStorage extends AbstractDocumentStorage<TodoModel, File> {

  @Override
  protected TodoModel createDocument() {
    return new TodoModel();
  }

  private TodoPersistence todoPersistence = new TodoPersistence();

  @Override
  public TodoModel loadDocument(InputStream inputStream) throws Exception {
    try (Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
      return todoPersistence.readTodoModel(reader);
    }
  }

  @Override
  public void saveDocument(TodoModel todoModel, File file) throws Exception {
    try (Writer writer = new FileWriter(file, StandardCharsets.UTF_8)) {
      todoPersistence.writeTodoModel(todoModel, writer);
    }
  }

  @Override
  protected InputStream toInputStream(final File location) throws IOException {
    return toFileInputStream((File) location);
  }
}
