package todolist.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import todolist.core.AbstractTodoList;
import todolist.core.TodoList;
import todolist.core.TodoModel;
import todolist.json.TodoModule;

/**
 * Class that centralizes access to a TodoModel. Makes it easier to support transparent use of a
 * REST API.
 */
public class RemoteTodoModelAccess implements TodoModelAccess {

  private final URI endpointBaseUri;

  private ObjectMapper objectMapper;

  private TodoModel todoModel;

  public RemoteTodoModelAccess(URI endpointBaseUri) {
    this.endpointBaseUri = endpointBaseUri;
    objectMapper = new ObjectMapper().registerModule(new TodoModule());
  }

  private TodoModel getTodoModel() {
    if (todoModel == null) {
      HttpRequest request = HttpRequest.newBuilder(endpointBaseUri)
          .header("Accept", "application/json")
          .GET()
          .build();
      try {
        final HttpResponse<String> response =
            HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
        final String responseString = response.body();
        this.todoModel = objectMapper.readValue(responseString, TodoModel.class);
        System.out.println("TodoModel: " + this.todoModel);
      } catch (IOException | InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
    return todoModel;
  }

  /**
   * Checks that name is valid for a (new) TodoList.
   *
   * @param name the (new) name
   * @return true if the name is value, false otherwise
   */
  @Override
  public boolean isValidTodoListName(String name) {
    return getTodoModel().isValidTodoListName(name);
  }

  /**
   * Checks if there (already) exists a TodoList with the provided name.
   *
   * @param name the (new) name
   * @return true if there exists a TodoList with the provided name, false otherwise
   */
  @Override
  public boolean hasTodoList(String name) {
    return getTodoModel().hasTodoList(name);
  }

  /**
   * Gets the names of the TodoLists.
   *
   * @return the names of the TodoLists.
   */
  @Override
  public Collection<String> getTodoListNames() {
    Collection<String> allNames = new ArrayList<>();
    getTodoModel().forEach(todoList -> allNames.add(todoList.getName()));
    return allNames;
  }

  private String uriParam(String s) {
    return URLEncoder.encode(s, StandardCharsets.UTF_8);
  }

  private URI todoListUri(String name) {
    return endpointBaseUri.resolve(uriParam(name));
  }

  /**
   * Gets the TodoList with the given name.
   *
   * @param name the TodoList's name
   * @return the TodoList with the given name
   */
  @Override
  public AbstractTodoList getTodoList(String name) {
    AbstractTodoList oldTodoList = this.todoModel.getTodoList(name);
    // if existing list has no todo items, try to (re)load
    if (oldTodoList == null || (! (oldTodoList instanceof TodoList))) {
      HttpRequest request =
          HttpRequest.newBuilder(todoListUri(name))
              .header("Accept", "application/json").GET().build();
      try {
        final HttpResponse<String> response =
            HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
        String responseString = response.body();
        System.out.println("getTodoList(" + name + ") response: " + responseString);
        AbstractTodoList todoList = objectMapper.readValue(responseString, AbstractTodoList.class);
        if (! (todoList instanceof TodoList)) {
          TodoList newTodoList = new TodoList(todoList.getName());
          newTodoList.setDeadline(todoList.getDeadline());
          todoList = newTodoList;
        }
        this.todoModel.putTodoList(todoList);
      } catch (IOException | InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
    return oldTodoList;
  }

  private void putTodoList(AbstractTodoList todoList) {
    try {
      String json = objectMapper.writeValueAsString(todoList);
      HttpRequest request = HttpRequest.newBuilder(todoListUri(todoList.getName()))
          .header("Accept", "application/json")
          .header("Content-Type", "application/json")
          .PUT(BodyPublishers.ofString(json))
          .build();
      final HttpResponse<String> response =
          HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
      String responseString = response.body();
      Boolean added = objectMapper.readValue(responseString, Boolean.class);
      if (added != null) {
        todoModel.putTodoList(todoList);
      }
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Adds a TodoList to the underlying TodoModel.
   *
   * @param todoList the TodoList
   */
  @Override
  public void addTodoList(AbstractTodoList todoList) {
    putTodoList(todoList);
  }

  /**
   * Removes the TodoList with the given name from the underlying TodoModel.
   *
   * @param name the name of the TodoList to remove
   */
  @Override
  public void removeTodoList(String name) {
    try {
      HttpRequest request = HttpRequest.newBuilder(todoListUri(name))
          .header("Accept", "application/json")
          .DELETE()
          .build();
      final HttpResponse<String> response =
          HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
      String responseString = response.body();
      Boolean removed = objectMapper.readValue(responseString, Boolean.class);
      if (removed != null) {
        todoModel.removeTodoList(todoModel.getTodoList(name));
      }
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Renames a TodoList to a new name.
   *
   * @param oldName the name of the TodoList to change
   * @param newName the new name
   */
  @Override
  public void renameTodoList(String oldName, String newName) {
    try {
      HttpRequest request = HttpRequest.newBuilder(todoListUri(oldName))
          .header("Accept", "application/json")
          .header("Content-Type", "application/x-www-form-urlencoded")
          .POST(BodyPublishers.ofString("newName=" + uriParam(newName)))
          .build();
      final HttpResponse<String> response =
          HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
      String responseString = response.body();
      Boolean renamed = objectMapper.readValue(responseString, Boolean.class);
      if (renamed != null) {
        todoModel.getTodoList(oldName).setName(newName);
      }
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Notifies that the TodoList has changed, e.g. TodoItems
   * have been mutated, added or removed.
   *
   * @param todoList the TodoList that has changed
   */
  @Override
  public void notifyTodoListChanged(AbstractTodoList todoList) {
    putTodoList(todoList);
  }
}