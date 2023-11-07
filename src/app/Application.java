package app;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.Entity;
import enums.Entities;
import models.Island;
import factories.EntityFactory;
import factories.PlantFactory;
import tasks.LifeCycle;
import util.RandomEnumGenerator;
import util.Utility;

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

            Thread thread = new Thread(lifeCycle);
            System.out.println(island.getIslandMap());
            thread.start();
            Thread.sleep(1);
            thread.interrupt();
            Thread.sleep(60000);
            System.out.println(island.getIslandMap());
            Thread.sleep(60000);
            System.out.println(island.getIslandMap());
            thread.interrupt();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        catch (InterruptedException e) {
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

        islandCellStream.forEach(list -> {
            int limitCountOfEntitiesOnCell = island.getConfig().getLimitCountOfEntitiesOnCell();
            while (limitCountOfEntitiesOnCell != 0) {
                Entities kind = randomEnumGenerator.randomEnum();
                int limit = kind.getRandomCountOnCell();

                if (limit <= limitCountOfEntitiesOnCell) {
                    for (int i = 0; i < limit; i++) {
                        Utility.createEntityAndAdd(list,kind);
                    }
                    limitCountOfEntitiesOnCell -= limit;
                }
            }
        });
    }
}
