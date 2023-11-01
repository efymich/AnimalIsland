package factories;

import entities.Entity;
import entities.Predator;
import entities.Entities;

public class PredatorFactory implements EntityFactory{
    @Override
    public Entity createEntity(Entities kind) {
        return switch (kind) {
            case WOLF -> new Predator(Entities.WOLF);
            case SNAKE -> new Predator(Entities.SNAKE);
            case FOX -> new Predator(Entities.FOX);
            case BEAR -> new Predator(Entities.BEAR);
            case HAWK -> new Predator(Entities.HAWK);
            default -> null;
        };
    }
}