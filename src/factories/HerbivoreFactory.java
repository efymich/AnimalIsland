package factories;

import models.Entity;
import enums.Entities;
import models.Herbivore;

public class HerbivoreFactory implements EntityFactory{
    @Override
    public Entity createEntity(Entities kind) {
        return switch (kind) {
            case HORSE -> new Herbivore(Entities.HORSE);
            case DEER -> new Herbivore(Entities.DEER);
            case RABBIT -> new Herbivore(Entities.RABBIT);
            case MOUSE -> new Herbivore(Entities.MOUSE);
            case GOAT -> new Herbivore(Entities.GOAT);
            case SHEEP -> new Herbivore(Entities.SHEEP);
            case BOAR -> new Herbivore(Entities.BOAR);
            case BUFFALO -> new Herbivore(Entities.BUFFALO);
            case DUCK -> new Herbivore(Entities.DUCK);
            case CATERPILLAR -> new Herbivore(Entities.CATERPILLAR);
            default -> null;
        };
    }
}
