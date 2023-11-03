package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import enums.Entities;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Map;
@Getter
@NoArgsConstructor
@ToString
public class EatingProbs implements Serializable {
    @JsonProperty
    private Map<Entities, Map<Entities,Double>> eatingProbsMap;
}
