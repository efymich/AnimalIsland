package factories;

import enums.Entities;
import models.Entity;
import models.Plant;

public class PlantFactory implements EntityFactory {
    @Override
    public Entity createEntity(Entities kind) {
        return switch (kind) {
            case PLANT -> new Plant(Entities.PLANT);
            default -> null;
        };
    }
}
