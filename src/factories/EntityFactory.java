package factories;

import entities.Entity;
import entities.Entities;

public interface EntityFactory {
    Entity createEntity(Entities kind);
}
