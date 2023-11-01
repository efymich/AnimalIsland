package factories;

import entities.Entity;
import entities.Entities;
import entities.Plant;

public class PlantFactory implements EntityFactory{
    @Override
    public Entity createEntity(Entities kind) {
        return switch (kind){
            case PLANT -> new Plant(Entities.PLANT);
            default -> null;
        };
    }
}
