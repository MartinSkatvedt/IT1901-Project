package todolist.springboot.restserver;

import com.fasterxml.jackson.databind.Module;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import todolist.json.TodoModule;

@SpringBootApplication
public class TodoModelApplication {

  @Bean
  public Module objectMapperModule() {
    return new TodoModule(false);
  }

  public static void main(String[] args) {
    SpringApplication.run(TodoModelApplication.class, args);
  }
}
