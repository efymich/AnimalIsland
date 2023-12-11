package app;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalTime;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Data
public class Configuration {

    private int xSize;
    private int ySize;
    private int limitCountOfEntitiesOnCell;
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime lifeCyclePeriod;
}
