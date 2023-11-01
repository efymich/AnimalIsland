package app;

import entities.Entity;
import entities.Entities;
import entities.EntityKind;
import entities.Island;
import factories.EntityFactory;
import factories.HerbivoreFactory;
import factories.PlantFactory;
import factories.PredatorFactory;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class Application {

    public void start(){
        Island island = configureApp();
        seedPlants(island);
        populateMap(island);

        System.out.println(island.getIslandMap());
    }
    private Island configureApp() {
        Configuration configuration = new Configuration(2, 3, LocalTime.of(0, 1),10);
        return Island.getInstance(configuration);
    }

    private void seedPlants(Island island) {
        Map<Integer, Map<Integer, ArrayList<Entity>>> islandMap = island.getIslandMap();
        EntityFactory plantFactory = new PlantFactory();

        islandMap.values()
                .stream()
                .flatMap(map -> map.values().stream())
                .forEach(list -> {
                    int limit = new Random().nextInt(0, Entities.PLANT.getCountOnCell());
                    for (int i = 0; i < limit; i++) {
                        list.add(plantFactory.createEntity(Entities.PLANT));
                    }
                });
    }

    private void populateMap(Island island) {
        Map<Integer, Map<Integer, ArrayList<Entity>>> islandMap = island.getIslandMap();
        Entities[] entities = Entities.values();
        PredatorFactory predatorFactory = new PredatorFactory();
        HerbivoreFactory herbivoreFactory = new HerbivoreFactory();

        islandMap.values()
                .stream()
                .flatMap(map -> map.values().stream())
                .forEach(list -> {
                    int limitCountOfEntitiesOnCell = island.getConfig().getLimitCountOfEntitiesOnCell();
                    while (limitCountOfEntitiesOnCell != 0){
                        Entities kind = entities[new Random().nextInt(entities.length)];
                        int limit = new Random().nextInt(0,kind.getCountOnCell());
                        if (limit <= limitCountOfEntitiesOnCell) {
                            for (int i = 0 ; i < limit; i++) {
                                if (kind.getEntityKind() == EntityKind.PREDATOR) {
                                    list.add(predatorFactory.createEntity(kind));
                                } else {
                                    list.add(herbivoreFactory.createEntity(kind));
                                }
                            }
                            limitCountOfEntitiesOnCell -= limit;
                        }
                    }
//                    for (int i = 0; i < limit; i++) {
//                        if (kind.getEntityKind() == EntityKind.PREDATOR) {
//                            list.add(predatorFactory.createEntity(kind));
//                        } else if (kind.getEntityKind() == EntityKind.HERBIVORE) {
//                            list.add(herbivoreFactory.createEntity(kind));
//                        } else continue;
//                    }
                });
    }

    private void generateStatistic(){}

}
