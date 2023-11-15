package factories;

import enums.Entities;
import models.Entity;

public interface EntityFactory {
    Entity createEntity(Entities kind);
}
