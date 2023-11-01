package app;

import entities.Entity;
import enums.Entities;
import enums.EntityKind;
import entities.Island;
import factories.EntityFactory;
import factories.HerbivoreFactory;
import factories.PlantFactory;
import factories.PredatorFactory;
import util.RandomEnumGenerator;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Stream;

import static util.Utility.getIslandCellStream;

public class Application {

    public void start() {
        Island island = configureApp();
        seedPlants(island);
        populateMap(island);

        System.out.println(island.getIslandMap());
    }

    private Island configureApp() {
        Configuration configuration = new Configuration(2, 3, LocalTime.of(0, 1), 10);
        return Island.getInstance(configuration);
    }

    private void seedPlants(Island island) {
        Stream<ArrayList<Entity>> islandCellStream = getIslandCellStream(island);
        EntityFactory plantFactory = new PlantFactory();

        islandCellStream.forEach(list -> {
            int limit = new Random().nextInt(0, Entities.PLANT.getCountOnCell());
            for (int i = 0; i < limit; i++) {
                list.add(plantFactory.createEntity(Entities.PLANT));
            }
        });
    }

    private void populateMap(Island island) {
        Stream<ArrayList<Entity>> islandCellStream = getIslandCellStream(island);
        RandomEnumGenerator<Entities> randomEnumGenerator = new RandomEnumGenerator<>(Entities.class);
        PredatorFactory predatorFactory = new PredatorFactory();
        HerbivoreFactory herbivoreFactory = new HerbivoreFactory();

        islandCellStream.forEach(list -> {
            int limitCountOfEntitiesOnCell = island.getConfig().getLimitCountOfEntitiesOnCell();
            while (limitCountOfEntitiesOnCell != 0) {
                Entities kind = randomEnumGenerator.randomEnum();
                int limit = kind.getRandomCountOnCell();

                if (limit <= limitCountOfEntitiesOnCell) {
                    for (int i = 0; i < limit; i++) {
                        if (kind.getEntityKind() == EntityKind.PREDATOR) {
                            list.add(predatorFactory.createEntity(kind));
                        } else {
                            list.add(herbivoreFactory.createEntity(kind));
                        }
                    }
                    limitCountOfEntitiesOnCell -= limit;
                }
            }
        });
    }

    private void generateStatistic() {
    }

}
