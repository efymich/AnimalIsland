package util;

import enums.Entities;
import enums.EntityKinds;
import factories.HerbivoreFactory;
import factories.PlantFactory;
import factories.PredatorFactory;
import models.Entity;
import models.Island;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Stream;

public class Utility {

    public static Stream<Entity> getIslandCellEntityStream(Island island) {
        Map<Integer, Map<Integer, ArrayList<Entity>>> islandMap = island.getIslandMap();
        return islandMap.values()
                .stream()
                .flatMap(map -> map.values().stream())
                .flatMap(ArrayList::stream);
    }
    public static Stream<ArrayList<Entity>> getIslandCellStream(Island island) {
        Map<Integer, Map<Integer, ArrayList<Entity>>> islandMap = island.getIslandMap();
        return islandMap.values()
                .stream()
                .flatMap(map -> map.values().stream());
    }

    public static void createEntityAndAdd(ArrayList<Entity> list, Entities kind){
        PredatorFactory predatorFactory = new PredatorFactory();
        HerbivoreFactory herbivoreFactory = new HerbivoreFactory();
        PlantFactory plantFactory = new PlantFactory();

        if (kind.getEntityKinds() == EntityKinds.PREDATOR) {
            list.add(predatorFactory.createEntity(kind));
        } else if (kind.getEntityKinds() == EntityKinds.HERBIVORE) {
            list.add(herbivoreFactory.createEntity(kind));
        } else {
            list.add(plantFactory.createEntity(kind));
        }
    }
}
