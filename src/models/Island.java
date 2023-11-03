package models;

import app.Configuration;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Getter
public final class Island {

    private static volatile Island instance;

    private final Configuration config;

    private final Map<Integer, Map<Integer, ArrayList<Entity>>> islandMap;

    private Island(Configuration config) {
        this.config = config;
        this.islandMap = generateMap(config);
    }

    private Map<Integer, Map<Integer, ArrayList<Entity>>> generateMap(Configuration config) {
        int x = config.getXSize();
        int y = config.getYSize();

        Map<Integer, Map<Integer, ArrayList<Entity>>> outerMap = new HashMap<>();
        for (int i = 0; i < x; i++) {
            Map<Integer, ArrayList<Entity>> innerMap = new HashMap<>();
            for (int j = 0; j < y; j++) {
                innerMap.put(j, new ArrayList<>());
                outerMap.put(i, innerMap);
            }
        }
        return outerMap;
    }

    public static Island getInstance(Configuration config) {
        if (instance == null){
            synchronized (Island.class) {
                if (instance == null) {
                    instance = new Island(config);
                }
                return instance;
            }
        }
        return instance;
    }
}
