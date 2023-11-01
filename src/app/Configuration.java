package app;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
public class Configuration implements Serializable {
    private int xSize;
    private int ySize;
    private LocalTime lifeCyclePeriod;
    private int limitCountOfEntitiesOnCell;
}
