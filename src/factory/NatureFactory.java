package factory;

import enums.Entities;
import model.Entity;
import model.Herbivore;
import model.Plant;
import model.Predator;

public class NatureFactory implements EntityFactory{
    @Override
    public Entity createEntity(Entities kind) {
        return switch (kind) {
            case DEER -> new Herbivore(Entities.DEER);
            case HORSE -> new Herbivore(Entities.HORSE);
            case MOUSE -> new Herbivore(Entities.MOUSE);
            case GOAT -> new Herbivore(Entities.GOAT);
            case SHEEP -> new Herbivore(Entities.SHEEP);
            case BOAR -> new Herbivore(Entities.BOAR);
            case RABBIT -> new Herbivore(Entities.RABBIT);
            case BUFFALO -> new Herbivore(Entities.BUFFALO);
            case DUCK -> new Herbivore(Entities.DUCK);
            case WOLF -> new Predator(Entities.WOLF);
            case CATERPILLAR -> new Herbivore(Entities.CATERPILLAR);
            case SNAKE -> new Predator(Entities.SNAKE);
            case FOX -> new Predator(Entities.FOX);
            case BEAR -> new Predator(Entities.BEAR);
            case HAWK -> new Predator(Entities.HAWK);
            case PLANT -> new Plant(Entities.PLANT);
            default -> throw new IllegalArgumentException("There is not such animal on island");
        };
    }
}
