package models;

import app.Configuration;
import enums.Entities;
import lombok.Getter;
import util.RandomEnumGenerator;
import util.Utility;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

import static util.Utility.getIslandCellStream;

@Getter
public final class Island {

    private static Island instance;

    private final Configuration config;

    private Map<Integer, Map<Integer, CopyOnWriteArrayList<Entity>>> islandMap = new HashMap<>();

    private Island(Configuration config) {
        this.config = config;
    }

    public static Island getInstance(Configuration config) {
        if (instance == null) {
            synchronized (Island.class) {
                if (instance == null) {
                    instance = new Island(config);
                    instance.initializeMap();
                    instance.populateMap();
                }
                return instance;
            }
        }
        return instance;
    }

    private void initializeMap() {
        this.islandMap = generateMap(config);
    }

    private void populateMap() {
        populateMap(this);
    }

    private Map<Integer, Map<Integer, CopyOnWriteArrayList<Entity>>> generateMap(Configuration config) {
        int x = config.getXSize();
        int y = config.getYSize();

        Map<Integer, Map<Integer, CopyOnWriteArrayList<Entity>>> outerMap = new HashMap<>();
        for (int i = 0; i < x; i++) {
            Map<Integer, CopyOnWriteArrayList<Entity>> innerMap = new HashMap<>();
            for (int j = 0; j < y; j++) {
                innerMap.put(j, new CopyOnWriteArrayList<>());
                outerMap.put(i, innerMap);
            }
        }
        return outerMap;
    }

    private void populateMap(Island island) {
        Stream<CopyOnWriteArrayList<Entity>> islandCellStream = getIslandCellStream(island);
        RandomEnumGenerator<Entities> randomEnumGenerator = new RandomEnumGenerator<>(Entities.class);

        islandCellStream.forEach(list -> {
            int limitCountOfEntitiesOnCell = island.getConfig().getLimitCountOfEntitiesOnCell();
            while (limitCountOfEntitiesOnCell != 0) {
                Entities kind = randomEnumGenerator.randomEnum();
                int limit = kind.getRandomCountOnCell();

                if (limit <= limitCountOfEntitiesOnCell) {
                    for (int i = 0; i < limit; i++) {
                        Utility.createEntityAndAdd(list, kind);
                    }
                    limitCountOfEntitiesOnCell -= limit;
                }
            }
        });
    }
}
