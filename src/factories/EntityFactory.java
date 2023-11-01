package factories;

import entities.Entity;
import enums.Entities;

public interface EntityFactory {
    Entity createEntity(Entities kind);
}
