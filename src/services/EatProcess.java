package services;

import com.fasterxml.jackson.databind.ObjectMapper;
import enums.Entities;
import lombok.Getter;
import models.EatingProbs;
import models.Entity;
import util.RandomGenerator;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Getter
public class EatProcess {
    private volatile static EatProcess instance;
    private final EatingProbs eatingProbs;

    private EatProcess(EatingProbs eatingProbs) {
        this.eatingProbs = eatingProbs;
    }

    public boolean isEatable(Entity whoEats, Entity victim) {
        RandomGenerator generator = new RandomGenerator();
        Map<Entities, Map<Entities, Double>> probsMap = eatingProbs.getEatingProbsMap();

        if (!probsMap.containsKey(whoEats.getKind())) return false;
        Double probability = eatingProbs.getEatingProbsMap().get(whoEats.getKind()).getOrDefault(victim.getKind(), 0.0);

        return generator.beEaten(probability);
    }

    public static EatProcess getInstance() {
        if (instance == null) {
            synchronized (EatProcess.class) {
                if (instance == null) {
                    try {
                        instance = new EatProcess(getSerializable());
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw new RuntimeException();
                    }
                }
                return instance;
            }
        }
        return instance;
    }

    private static EatingProbs getSerializable() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File("resources/config/eatingprobs.json"), EatingProbs.class);
    }
}
