package enums;

import lombok.Getter;

import java.util.Random;

@Getter
public enum Entities {
    WOLF(EntityKinds.PREDATOR, 50, 30, 3, 8),
    SNAKE(EntityKinds.PREDATOR, 15, 30, 1, 3),
    FOX(EntityKinds.PREDATOR, 8, 30, 2, 2),
    BEAR(EntityKinds.PREDATOR, 500, 5, 2, 80),
    HAWK(EntityKinds.PREDATOR, 6, 20, 3, 1),
    HORSE(EntityKinds.HERBIVORE, 400, 20, 4, 60),
    DEER(EntityKinds.HERBIVORE, 300, 20, 4, 50),
    RABBIT(EntityKinds.HERBIVORE, 2, 150, 2, 0.45),
    MOUSE(EntityKinds.HERBIVORE, 0.05, 500, 1, 0.01),
    GOAT(EntityKinds.HERBIVORE, 60, 140, 3, 10),
    SHEEP(EntityKinds.HERBIVORE, 70, 140, 3, 15),
    BOAR(EntityKinds.HERBIVORE, 400, 50, 2, 50),
    BUFFALO(EntityKinds.HERBIVORE, 700, 10, 3, 100),
    DUCK(EntityKinds.HERBIVORE, 1, 200, 4, 0.15),
    CATERPILLAR(EntityKinds.HERBIVORE, 0.01, 1000, 0, 0),
    PLANT(EntityKinds.PLANT, 1, 3, 0, 0);

    private final EntityKinds entityKinds;
    private final double weight;
    private final int countOnCell;
    private final int speed;
    private final double saturationLimit;
    private static final Random random = new Random();

    Entities(EntityKinds entityKinds, double weight, int countOnCell, int speed, double saturationLimit) {
        this.entityKinds = entityKinds;
        this.weight = weight;
        this.countOnCell = countOnCell;
        this.speed = speed;
        this.saturationLimit = saturationLimit;
    }
    //TODO: непонятно для чего рандом
    public int getRandomCountOnCell() {
        return random.nextInt(this.countOnCell);
    }
}
