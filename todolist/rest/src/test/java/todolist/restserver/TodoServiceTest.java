package todolist.restserver;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import java.util.Iterator;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import todolist.core.AbstractTodoList;
import todolist.core.TodoModel;
import todolist.restapi.TodoModelService;

public class TodoServiceTest extends JerseyTest {

  protected boolean shouldLog() {
    return false;
  }

  @Override
  protected ResourceConfig configure() {
    final TodoConfig config = new TodoConfig();
    if (shouldLog()) {
      enable(TestProperties.LOG_TRAFFIC);
      enable(TestProperties.DUMP_ENTITY);
      config.property(LoggingFeature.LOGGING_FEATURE_LOGGER_LEVEL_SERVER, "WARNING");
    }
    return config;
  }

  private ObjectMapper objectMapper;

  @BeforeEach
  @Override
  public void setUp() throws Exception {
    super.setUp();
    objectMapper = new TodoModuleObjectMapperProvider().getContext(getClass());
  }

  @AfterEach
  @Override
  public void tearDown() throws Exception {
    super.tearDown();
  }

  @Test
  public void testGet_todo() {
    Response getResponse = target(TodoModelService.TODO_MODEL_SERVICE_PATH)
        .request(MediaType.APPLICATION_JSON + ";" + MediaType.CHARSET_PARAMETER + "=UTF-8")
        .get();
    assertEquals(200, getResponse.getStatus());
    try {
      TodoModel todoModel = objectMapper.readValue(getResponse.readEntity(String.class), TodoModel.class);
      Iterator<AbstractTodoList> it = todoModel.iterator();
      assertTrue(it.hasNext());
      AbstractTodoList todoList1 = it.next();
      assertTrue(it.hasNext());
      AbstractTodoList todoList2 = it.next();
      assertFalse(it.hasNext());
      assertEquals("todo1", todoList1.getName());
      assertEquals("todo2", todoList2.getName());
    } catch (JsonProcessingException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testGet_todo_todo1() {
    Response getResponse = target(TodoModelService.TODO_MODEL_SERVICE_PATH)
        .path("todo1")
        .request(MediaType.APPLICATION_JSON + ";" + MediaType.CHARSET_PARAMETER + "=UTF-8")
        .get();
    assertEquals(200, getResponse.getStatus());
    try {
      AbstractTodoList todoList = objectMapper.readValue(getResponse.readEntity(String.class), AbstractTodoList.class);
      assertEquals("todo1", todoList.getName());
    } catch (JsonProcessingException e) {
      fail(e.getMessage());
    }
  }
}