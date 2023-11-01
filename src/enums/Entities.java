package enums;

import lombok.Getter;

import java.util.Random;

@Getter
public enum Entities {
    WOLF(EntityKind.PREDATOR,50,30,3,8),
    SNAKE(EntityKind.PREDATOR,15,30,1,3),
    FOX(EntityKind.PREDATOR,8,30,2,2),
    BEAR(EntityKind.PREDATOR,500,5,2,80),
    HAWK(EntityKind.PREDATOR,6,20,3,1),
    HORSE(EntityKind.HERBIVORE,400,20,4,60),
    DEER(EntityKind.HERBIVORE,300,20,4,50),
    RABBIT(EntityKind.HERBIVORE,2,150,2,0.45),
    MOUSE(EntityKind.HERBIVORE,0.05,500,1,0.01),
    GOAT(EntityKind.HERBIVORE,60,140,3,10),
    SHEEP(EntityKind.HERBIVORE,70,140,3,15),
    BOAR(EntityKind.HERBIVORE,400,50,2,50),
    BUFFALO(EntityKind.HERBIVORE,700,10,3,100),
    DUCK(EntityKind.HERBIVORE,1,200,4,0.15),
    CATERPILLAR(EntityKind.HERBIVORE,0.01,1000,0,0),
    PLANT(EntityKind.PLANT,1,3,0,0);

    private final EntityKind entityKind;
    private final double weight;
    private final int countOnCell;
    private final int speed;
    private final double saturationLimit;
    private static final Random random = new Random();

    Entities(EntityKind entityKind,double weight, int countOnCell, int speed, double saturationLimit) {
        this.entityKind = entityKind;
        this.weight = weight;
        this.countOnCell = countOnCell;
        this.speed = speed;
        this.saturationLimit = saturationLimit;
    }

    public int getRandomCountOnCell(){
        return random.nextInt(this.countOnCell);
    }
    public int getRandomSpeed(){
        return random.nextInt(this.speed);
    }
}
