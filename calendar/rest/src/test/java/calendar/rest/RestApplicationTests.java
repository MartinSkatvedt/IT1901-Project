package calendar.rest;

import java.time.LocalDate;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import calendar.core.Event;
import calendar.core.RequestEvent;
import calendar.core.User;
import calendar.rest.UserService;

@SpringBootTest
@AutoConfigureMockMvc
class RestApplicationTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    private User testUser;

    private Event testEvent;

    @BeforeEach
    public void setup() {
        testUser = new User("testuser");
        testEvent = new Event("event1", "desc1", LocalDate.of(2021, 12, 9), "10:00");
    }

    @Test
    public void testGetUser() throws Exception {
        User user = new User("newuser");

        when(userService.getUser(any(String.class))).thenReturn(user);

        mvc.perform(get("/user/{username}", user.getUsername()).contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"username\": \"newuser\",\"calendar\": {\"events\": []}}"));
    }

    @Test
    public void testGetExistingUser() throws Exception {
        testUser.getCalendar().addEvent(testEvent);

        when(userService.getUser(testUser.getUsername())).thenReturn(testUser);

        mvc.perform(get("/user/{username}", testUser.getUsername()).contentType("application/json"))
                .andExpect(status().isOk());

        MvcResult result = mvc.perform(get("/user/{username}", testUser.getUsername()).contentType("application/json"))
                .andReturn();
        String response = result.getResponse().getContentAsString();
        assertEquals(objectMapper.writeValueAsString(testUser), response);
    }

    @Test
    public void testGetCalendar() throws Exception {
        User user = new User("user");
        user.getCalendar().addEvent(new Event("event1", "desc1", LocalDate.of(2021, 12, 9), "10:00"));
        when(userService.getCalendar(user.getUsername())).thenReturn(user.getCalendar());

        mvc.perform(get("/calendar/{username}", user.getUsername()).contentType("application/json"))
                .andExpect(status().isOk());

        MvcResult result = mvc.perform(get("/calendar/{username}", user.getUsername()).contentType("application/json"))
                .andReturn();
        String response = result.getResponse().getContentAsString();

        assertEquals(objectMapper.writeValueAsString(user.getCalendar()), response);
    }

    @Test
    public void testGetEvent() throws Exception {
        User user = new User("test");
        Event event = new Event("event1", "desc1", LocalDate.of(2021, 12, 9), "10:00");
        user.getCalendar().addEvent(event);
        when(userService.getEvent(user.getUsername(), event.getId())).thenReturn(event);

        mvc.perform(get("/calendar/{username}/{id}", user.getUsername(), event.getId()).contentType("application/json"))
                .andExpect(status().isOk());

        MvcResult result = mvc.perform(
                get("/calendar/{username}/{id}", user.getUsername(), event.getId()).contentType("application/json"))
                .andReturn();
        String response = result.getResponse().getContentAsString();

        assertEquals(objectMapper.writeValueAsString(user.getCalendar().getEvent(event.getId())), response);
    }

    @Test
    public void testReplaceEvent() throws Exception {
        RequestEvent reqEvent = new RequestEvent();
        reqEvent.setHeader("new header");
        reqEvent.setDescription("new description");
        reqEvent.setDate("2022-10-31");
        reqEvent.setTime("12:00");
        Event newEvent = new Event("new header", "new description", LocalDate.of(2022, 10, 31), "12:00");
        when(userService.replaceEvent(reqEvent, testUser.getUsername(), 1)).thenReturn(newEvent);

        mvc.perform(put("/calendar/{username}/{id}", testUser.getUsername(), 1).contentType("application/json")
                .content(objectMapper.writeValueAsString(reqEvent))).andExpect(status().isOk());
    }

    @Test
    public void testPostEvent() throws Exception {
        RequestEvent reqEvent = new RequestEvent();
        reqEvent.setHeader("new header");
        reqEvent.setDescription("new description");
        reqEvent.setDate("2022-10-31");
        reqEvent.setTime("12:00");
        Event newEvent = new Event("new header", "new description", LocalDate.of(2022, 10, 31), "12:00");
        newEvent.setId(15);
        when(userService.postEvent(any(RequestEvent.class), any(String.class))).thenReturn(newEvent);

        mvc.perform(post("/calendar/{username}", testUser.getUsername()).contentType("application/json")
                .content(objectMapper.writeValueAsString(reqEvent))).andExpect(status().isCreated());

    }

    @Test
    public void testDeleteEvent() throws Exception {
        when(userService.deleteEvent(testUser.getUsername(), 1)).thenReturn(testEvent);

        mvc.perform(delete("/calendar/{username}/{id}", testUser.getUsername(), 1)).andExpect(status().isOk());
    }

}
