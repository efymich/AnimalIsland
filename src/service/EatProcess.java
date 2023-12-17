package service;

import enums.Entities;
import lombok.Getter;
import model.EatingProperties;
import model.Entity;
import utilize.RandomGenerator;

import java.util.Map;

@Getter
public class EatProcess {
    private volatile static EatProcess instance;
    private final EatingProperties eatingProperties = ConfigManager.getConfigManager().getEatingProperties();
    private final RandomGenerator generator;

    private EatProcess(RandomGenerator generator) {
        this.generator = generator;
    }

    public boolean isEatable(Entity whoEats, Entity victim) {
        Map<Entities, Map<Entities, Double>> probsMap = eatingProperties.getEatingProbsMap();
        if (!probsMap.containsKey(whoEats.getKind())) return false;
        Double probability = eatingProperties.getEatingProbsMap()
                .get(whoEats.getKind())
                .getOrDefault(victim.getKind(), 0.0);

        return generator.beEaten(probability);
    }

    public static EatProcess getInstance() {
        if (instance == null) {
            synchronized (EatProcess.class) {
                if (instance == null) {
                    RandomGenerator generator = new RandomGenerator();
                    instance = new EatProcess(generator);
                }
                return instance;
            }
        }
        return instance;
    }
}
