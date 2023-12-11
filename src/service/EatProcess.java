package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import enums.Entities;
import lombok.Getter;
import model.EatingProbs;
import model.Entity;
import utilize.RandomGenerator;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Getter
public class EatProcess {
    private volatile static EatProcess instance;
    private final EatingProbs eatingProbs;
    private final RandomGenerator generator;

    private EatProcess(EatingProbs eatingProbs, RandomGenerator generator) {
        this.eatingProbs = eatingProbs;
        this.generator = generator;
    }

    public boolean isEatable(Entity whoEats, Entity victim) {
        //TODO: не делать переменные через new - не тестируемое
        Map<Entities, Map<Entities, Double>> probsMap = eatingProbs.getEatingProbsMap();
        if (!probsMap.containsKey(whoEats.getKind())) return false;
        Double probability = eatingProbs.getEatingProbsMap()
                .get(whoEats.getKind())
                .getOrDefault(victim.getKind(), 0.0);

        return generator.beEaten(probability);
    }

    public static EatProcess getInstance() {
        if (instance == null) {
            synchronized (EatProcess.class) {
                if (instance == null) {
                    try {
                        RandomGenerator generator = new RandomGenerator();
                        instance = new EatProcess(getSerializable(), generator);
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
    //TODO: единственная ответственность! нужно вынести куда-то
    private static EatingProbs getSerializable() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File("resources/config/eatingprobs.json"), EatingProbs.class);
    }
}
