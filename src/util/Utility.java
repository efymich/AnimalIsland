package util;

import models.Entity;
import models.Island;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Stream;

public class Utility {

    public static Stream<Map<Integer,ArrayList<Entity>>> getIslandRowStream(Island island) {
        Map<Integer, Map<Integer, ArrayList<Entity>>> islandMap = island.getIslandMap();
        return islandMap.values()
                .stream();
    }
    public static Stream<ArrayList<Entity>> getIslandCellStream(Island island) {
        Map<Integer, Map<Integer, ArrayList<Entity>>> islandMap = island.getIslandMap();
        return islandMap.values()
                .stream()
                .flatMap(map -> map.values().stream());
    }
}
