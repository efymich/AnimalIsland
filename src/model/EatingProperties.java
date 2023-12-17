package model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import enums.Entities;
import lombok.Data;

import java.util.Map;
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Data
public class EatingProperties {
    private Map<Entities, Map<Entities, Double>> eatingProbsMap;
}
