package factories;

import models.Entity;
import enums.Entities;
import models.Plant;

public class PlantFactory implements EntityFactory{
    @Override
    public Entity createEntity(Entities kind) {
        return switch (kind){
            case PLANT -> new Plant(Entities.PLANT);
            default -> null;
        };
    }
}
