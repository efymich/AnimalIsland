package app;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.Entity;
import enums.Entities;
import enums.EntityKinds;
import models.Island;
import factories.EntityFactory;
import factories.HerbivoreFactory;
import factories.PlantFactory;
import factories.PredatorFactory;
import tasks.LifeCycle;
import util.RandomEnumGenerator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Stream;

import static util.Utility.getIslandCellStream;

public class Application {

    public void start() {
        try {
            Island island = configureApp();
            seedPlants(island);
            populateMap(island);
            LifeCycle lifeCycle = new LifeCycle(island);

            lifeCycle.run();

            System.out.println(island.getIslandMap());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Island configureApp() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules(); // for working with Java 8 LocalData classes
        Configuration configuration = mapper.readValue(new File("resources/config/configuration.json"), Configuration.class);
        System.out.println(configuration);
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
        PlantFactory plantFactory = new PlantFactory();

        islandCellStream.forEach(list -> {
            int limitCountOfEntitiesOnCell = island.getConfig().getLimitCountOfEntitiesOnCell();
            while (limitCountOfEntitiesOnCell != 0) {
                Entities kind = randomEnumGenerator.randomEnum();
                int limit = kind.getRandomCountOnCell();

                if (limit <= limitCountOfEntitiesOnCell) {
                    for (int i = 0; i < limit; i++) {
                        if (kind.getEntityKinds() == EntityKinds.PREDATOR) {
                            list.add(predatorFactory.createEntity(kind));
                        } else if (kind.getEntityKinds() == EntityKinds.HERBIVORE) {
                            list.add(herbivoreFactory.createEntity(kind));
                        } else {
                            list.add(plantFactory.createEntity(kind));
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
