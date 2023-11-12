package services;

import enums.Directions;
import models.Entity;
import util.RandomEnumGenerator;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class MoveProcess {
    private volatile static MoveProcess instance;

    private MoveProcess() {
    }

    public void move(Map<Integer, Map<Integer, CopyOnWriteArrayList<Entity>>> map, int xKey, int yKey, Entity entity) {
        Directions direction = new RandomEnumGenerator<>(Directions.class).randomEnum();
        int countOfSteps = new Random().nextInt(entity.getSpeed());
        int newXKey;
        int newYKey;

        switch (direction) {
            case UP -> {
                newXKey = xKey;
                newYKey = yKey - countOfSteps;

                if (newYKey >= 0) {
                    CopyOnWriteArrayList<Entity> list = map.get(newXKey).get(newYKey);
                    if (!isEnoughAnimals(list,entity)) {
                        list.add(entity);
                    }
                }
            }
            case LEFT -> {
                newXKey = xKey - countOfSteps;
                newYKey = yKey;

                if (newXKey >= 0) {
                    CopyOnWriteArrayList<Entity> list = map.get(newXKey).get(newYKey);
                    if (!isEnoughAnimals(list,entity)) {
                        list.add(entity);
                    }
                }
            }
            case RIGHT -> {
                newXKey = xKey + countOfSteps;
                newYKey = yKey;

                if (newXKey < map.size()) {
                    CopyOnWriteArrayList<Entity> list = map.get(newXKey).get(newYKey);
                    if (!isEnoughAnimals(list,entity)) {
                        list.add(entity);
                    }
                }
            }
            case DOWN ->{
                newXKey = xKey;
                newYKey = yKey + countOfSteps;

                if (newYKey < map.get(newXKey).size()) {
                    CopyOnWriteArrayList<Entity> list = map.get(newXKey).get(newYKey);
                    if (!isEnoughAnimals(list,entity)) {
                        list.add(entity);
                    }
                }
            }
        }
    }

    public static MoveProcess getInstance() {
        if (instance == null) {
            synchronized (EatProcess.class) {
                if (instance == null) {
                    instance = new MoveProcess();
                }
                return instance;
            }
        }
        return instance;
    }

    private boolean isEnoughAnimals(List<Entity> list, Entity entity) {
        int countOnCell = entity.getCountOnCell();
        return list.stream()
                .filter(e -> e.getKind() == entity.getKind())
                .count() > countOnCell;
    }
}
