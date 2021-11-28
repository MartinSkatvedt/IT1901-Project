package calendar.json;

import calendar.core.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Class for having persistance with json
 */
public class UserPersistence {
    private ObjectMapper mapper;
    private Path saveFilePath = null;  
    
    /**
     * Constructor which sets up objectMapper
     */
    public UserPersistence() {
        mapper = new ObjectMapper();
        mapper.registerModule(new UserModule());
    }

    /**
    * @param reader
    * @return the user read for file
    * @throws IOException
    */
    public User readUser(Reader reader) throws IOException {
        return mapper.readValue(reader, User.class);
    }

    /**
    * @param user
    * @param writer
    * @throws IOException
    */
    public void writeUser(User user, Writer writer) throws IOException {
        mapper.writerWithDefaultPrettyPrinter().writeValue(writer, user);
    }

    /**
    * Sets the path to the savefile
    * 
    * @param saveFile path to savefile
    */
    public void setSaveFile(String saveFile) {
        this.saveFilePath = Paths.get(System.getProperty("user.home"), saveFile);
    }

    /**
    * Loads a user from the saved file (saveFilePath) in the user.home folder.
    *
    * @return the loaded user
    */
    public User loadUser() throws IOException, IllegalStateException, FileNotFoundException {
        if (saveFilePath == null) {
            throw new IllegalStateException("Save file path is not set, yet");
        }
        try (Reader reader = new FileReader(saveFilePath.toFile(), StandardCharsets.UTF_8)) {
            return readUser(reader);
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    /**
    * Saves a user to the saveFilePath in the user.home folder.
    * 
    * @param user the user to save
    */
    public void saveUser(User user) throws IOException, IllegalStateException {
        if (saveFilePath == null) {
            throw new IllegalStateException("Save file path is not set, yet");
        }
        try (Writer writer = new FileWriter(saveFilePath.toFile(), StandardCharsets.UTF_8)) {
            writeUser(user, writer);
        }
    }
}
