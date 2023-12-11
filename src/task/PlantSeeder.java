package task;

import enums.Entities;
import model.Entity;
import model.Island;
import utilize.Utility;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class PlantSeeder implements Runnable {

    private final Island island;

    public PlantSeeder(Island island) {
        this.island = island;
    }

    @Override
    public void run() {
        seedPlants(island);
    }

    private void seedPlants(Island island) {
        Map<Integer, Map<Integer, CopyOnWriteArrayList<Entity>>> islandMap = island.getIslandMap();

        islandMap.values()
                .stream()
                .flatMap(map -> map.values().stream())
                .forEach(list -> {
                    int limit = new Random().nextInt(0, Entities.PLANT.getCountOnCell());
                    for (int i = 0; i < limit; i++) {
                        Utility.createEntityAndAdd(list,Entities.PLANT);
                    }
                });
    }
}
