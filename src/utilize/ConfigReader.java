package utilize;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;

import java.io.File;
import java.io.IOException;

@UtilityClass
public class ConfigReader {

    public static  <T> T readConfig(String filePath, Class<T> aClass) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.findAndRegisterModules();
            return objectMapper.readValue(new File(filePath), aClass);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
