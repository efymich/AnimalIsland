package factory;

import enums.Entities;
import model.Entity;

public interface EntityFactory {
    Entity createEntity(Entities kind);
}
