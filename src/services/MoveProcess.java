package services;

import app.Configuration;
import enums.Directions;
import models.Entity;
import models.Island;
import util.RandomEnumGenerator;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class MoveProcess {
    private volatile static MoveProcess instance;

    private MoveProcess() {
    }

    public void move(Island island, int xKey, int yKey, Entity entity) {

        Map<Integer, Map<Integer, CopyOnWriteArrayList<Entity>>> islandMap = island.getIslandMap();
        if (entity.getSpeed() != 0) {
            int[] initCoordinates = new int[]{xKey, yKey};
            int[] newCoordinates = getDirectionAndCoordinates(entity, initCoordinates);

            if (isEnoughSpace(newCoordinates, island)) {
                CopyOnWriteArrayList<Entity> initList = islandMap.get(initCoordinates[0]).get(initCoordinates[1]);
                CopyOnWriteArrayList<Entity> destinationList = islandMap.get(newCoordinates[0]).get(newCoordinates[1]);
                if (!isEnoughAnimals(destinationList, entity)) {
                    destinationList.add(entity);
                    initList.remove(entity);
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

    private int[] getDirectionAndCoordinates(Entity entity, int[] coordinatesArr) {
        Directions direction = new RandomEnumGenerator<>(Directions.class).randomEnum();
        int countOfSteps = new Random().nextInt(entity.getSpeed());

        // index 0 - X coordinate, index 1 - Y coordinate
        switch (direction) {
            case UP -> {
                coordinatesArr[1] -= countOfSteps;
            }
            case LEFT -> {
                coordinatesArr[0] -= countOfSteps;
            }
            case RIGHT -> {
                coordinatesArr[0] += countOfSteps;
            }
            case DOWN -> {
                coordinatesArr[1] += countOfSteps;
            }
        }

        return coordinatesArr;
    }

    private boolean isEnoughSpace(int[] coordinatesArr, Island island) {
        int maxY = island.getConfig().getYSize();
        int maxX = island.getConfig().getXSize();
        int minY = 0, minX = 0;

        return (coordinatesArr[0] < maxX && coordinatesArr[0] >= minX) && (coordinatesArr[1] < maxY && coordinatesArr[1] >= minY);
    }
}
