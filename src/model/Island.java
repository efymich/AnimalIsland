package model;

import app.IslandConfiguration;
import enums.Entities;
import lombok.Getter;
import utilize.RandomEnumGenerator;
import utilize.Utility;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

import static utilize.Utility.getIslandCellStream;

@Getter
public final class Island {

    private static Island instance;

    private final IslandConfiguration config;

    private Map<Integer, Map<Integer, CopyOnWriteArrayList<Entity>>> islandMap = new HashMap<>();

    private Island(IslandConfiguration config) {
        this.config = config;
    }

    public static Island getInstance(IslandConfiguration config) {
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

    private Map<Integer, Map<Integer, CopyOnWriteArrayList<Entity>>> generateMap(IslandConfiguration config) {
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
        Entities[] entities = Entities.values();
        islandCellStream.forEach(list -> {
            for (Entities entity : entities) {
                Entities kind = randomEnumGenerator.randomEnum();
                int limit = kind.getRandomCountOnCell();
                for (int i = 0; i < limit; i++) {
                    Utility.createEntityAndAdd(list, kind);
                }
            }
        });
        
        /* islandCellStream.forEach(list -> {
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
        */
    }
}
