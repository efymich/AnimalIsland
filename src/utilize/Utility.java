package utilize;

import enums.Entities;
import factory.NatureFactory;
import model.Entity;
import model.Island;

import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

public class Utility {

    public static Stream<Entity> getIslandCellEntityStream(Island island) {
        Map<Integer, Map<Integer, CopyOnWriteArrayList<Entity>>> islandMap = island.getIslandMap();
        return islandMap.values()
                .stream()
                .flatMap(map -> map.values().stream())
                .flatMap(CopyOnWriteArrayList::stream);
    }

    public static Stream<CopyOnWriteArrayList<Entity>> getIslandCellStream(Island island) {
        Map<Integer, Map<Integer, CopyOnWriteArrayList<Entity>>> islandMap = island.getIslandMap();
        return islandMap.values()
                .stream()
                .flatMap(map -> map.values().stream());
    }

    public static void createEntityAndAdd(CopyOnWriteArrayList<Entity> list, Entities kind) {
        NatureFactory natureFactory = new NatureFactory();
        list.add(natureFactory.createEntity(kind));
    }
}
