package todolist.ui;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fxutil.doc.DocumentListener;
import fxutil.doc.FileMenuController;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.fxml.FXML;
import todolist.core.TodoModel;

public class TodoDocumentAppController implements DocumentListener<TodoModel, File> {

  private final TodoModelStorage todoModelStorage;

  public TodoDocumentAppController() {
    todoModelStorage = new TodoModelStorage();
    todoModelStorage.addDocumentStorageListener(this);
  }
  
  public TodoModel getTodoModel() {
    return todoModelStorage.getDocument();
  }

  // to make it testable
  void setTodoModel(final TodoModel todoModel) {
    todoModelStorage.setDocument(todoModel);
    todoModelViewController.setTodoModelAccess(new DirectTodoModelAccess(getTodoModel()));
  }

  @FXML
  String userAppConfigPath;

  @FXML
  FileMenuController fileMenuController;

  @FXML
  TodoModelController todoModelViewController;

  /**
   * Map of config data. Current contents:
   * fileMenu.recentFiles = [ ... ]
   */
  
  @FXML
  private void initialize() {
    fileMenuController.setDocumentStorage(todoModelStorage);
    todoModelStorage.addDocumentStorageListener(this);
    applyConfig();
    if (! fileMenuController.openMostRecentFile()) {
      todoModelStorage.newDocument();
    }
  }

  private JsonNode config;
  private ObjectMapper configMapper = new ObjectMapper();

  private void applyConfig() {
    Path configPath = Paths.get(System.getProperty("user.home"), userAppConfigPath);
    if (userAppConfigPath != null) {
      try {
        config = configMapper.readTree(configPath.toFile());
      } catch (IOException ioex) {
        System.err.println("Fant ingen " + userAppConfigPath + " på hjemmeområdet");
      }
    }
    if (config == null) {
      try (InputStream input = getClass().getResourceAsStream("todo-config.json")) {
        config = configMapper.readTree(input);
      } catch (IOException e) {
        // ignore
      }
    }
    if (config == null) {
      config = JsonNodeFactory.instance.objectNode();
    }
    if (config != null) {
      ArrayNode recentFiles = getConfigProperty("fileMenu", "recentFiles");
      if (recentFiles != null) {
        List<File> recentFilesList = new ArrayList<>();
        recentFiles.forEach(element -> recentFilesList.add(new File(element.asText())));
        fileMenuController.addRecentFiles(recentFilesList.toArray(new File[0]));
      }
    }
  }

  void writeConfig() {
    Path configPath = Paths.get(System.getProperty("user.home"), userAppConfigPath);
    ArrayNode recentFilesArray = JsonNodeFactory.instance.arrayNode();
    for (File file : fileMenuController.getRecentFiles()) {
      recentFilesArray.add(JsonNodeFactory.instance.textNode(file.toString()));
    }
    setConfigProperty(recentFilesArray, "fileMenu", "recentFiles");
    try {
      configMapper.writeValue(configPath.toFile(), config);
    } catch (IOException ioe) {
      System.out.println("Fikk ikke skrevet konfigurasjon til " + userAppConfigPath
          + " på hjemmeområdet");
    }
  }

  private <T extends JsonNode> T getConfigProperty(String... path) {
    JsonNode node = config;
    for (String segment : path) {
      if (node instanceof ObjectNode objectNode) {
        node = objectNode.get(segment);
      } else {
        return null;
      }
    }
    return (T) node;
  }

  private void setConfigProperty(JsonNode newNode, String... path) {
    JsonNode node = config;
    Iterator<String> segments = List.of(path).iterator();
    while (segments.hasNext()) {
      String segment = segments.next();
      if (node instanceof ObjectNode objectNode) {
        if (! segments.hasNext()) {
          objectNode.set(segment, newNode);
          return;
        } else {
          ObjectNode objectNode2 = JsonNodeFactory.instance.objectNode();
          objectNode.set(segment, objectNode2);
          node = objectNode2;
        }
      }
    }
  }

  // DocumentListener

  @Override
  public void documentLocationChanged(final File documentLocation, final File oldDocumentLocation) {
  }

  @Override
  public void documentChanged(final TodoModel document, final TodoModel oldDocument) {
    todoModelViewController.setTodoModelAccess(new DirectTodoModelAccess(getTodoModel()));
  }
}
