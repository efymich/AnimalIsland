package provider;

import enums.Directions;
import model.Entity;
import model.Island;
import utilize.RandomEnumGenerator;

import java.util.List;
import java.util.Random;

public class MoveCoordinator {
    public boolean isEnoughAnimals(List<Entity> list, Entity entity) {
        int countOnCell = entity.getCountOnCell();
        return list.stream()
                .filter(e -> e.getKind() == entity.getKind())
                .count() > countOnCell;
    }

    public int[] getDirectionAndCoordinates(Entity entity, int[] coordinatesArr) {
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

    public boolean isEnoughSpace(int[] coordinatesArr, Island island) {
        int maxY = island.getConfig().getYSize();
        int maxX = island.getConfig().getXSize();
        int minY = 0, minX = 0;

        return (coordinatesArr[0] < maxX && coordinatesArr[0] >= minX) && (coordinatesArr[1] < maxY && coordinatesArr[1] >= minY);
    }
}
