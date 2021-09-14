package todolist.springboot.restserver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import java.util.Iterator;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import todolist.core.AbstractTodoList;
import todolist.core.TodoModel;
import todolist.json.TodoModule;

@AutoConfigureMockMvc
@ContextConfiguration(classes = { TodoModelController.class, TodoModelService.class, TodoModelApplication.class })
@WebMvcTest
public class TodoModelApplicationTest {

  @Autowired
  private MockMvc mockMvc;

  private ObjectMapper objectMapper;

  @BeforeEach
  public void setup() throws Exception {
    objectMapper = new ObjectMapper().registerModule(new TodoModule(false));;
  }

  private String todoUrl(String... segments) {
    String url = "/" + TodoModelController.TODO_MODEL_SERVICE_PATH;
    for (String segment : segments) {
      url = url + "/" + segment;
    }
    return url;
  }

  @Test
  public void testGet_todo() throws Exception {
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(todoUrl())
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();
    try {
      TodoModel todoModel = objectMapper.readValue(result.getResponse().getContentAsString(), TodoModel.class);
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
}
