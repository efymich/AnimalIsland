package factories;

import models.Entity;
import enums.Entities;

public interface EntityFactory {
    Entity createEntity(Entities kind);
}
