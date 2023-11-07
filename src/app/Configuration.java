package app;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import enums.Entities;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Configuration implements Serializable {
    @JsonProperty
    private int xSize;
    @JsonProperty
    private int ySize;
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime lifeCyclePeriod;
    @JsonProperty
    private int limitCountOfEntitiesOnCell;
}
