package tasks;

import enums.Entities;
import models.Entity;
import models.Island;
import services.EatProcess;
import services.MoveProcess;
import util.Utility;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class LifeCycle implements Runnable {
    private final Island island;
    private final EatProcess eatProcess = EatProcess.getInstance();
    private final MoveProcess moveProcess = MoveProcess.getInstance();

    public LifeCycle(Island island) {
        this.island = island;
    }

    @Override
    public void run() {
        while (true) {
            eating(island);
            reproducing(island);
            moving(island);
        }
    }

    private void eating(Island island) {
        Utility.getIslandCellStream(island)
                .forEach(list -> {
                    AtomicInteger innerAtom = new AtomicInteger(1);
                    for (int i = 0; i < list.size(); i++) {
                        double saturation = 0.0;

                        for (int j = i + 1; j < list.size(); j++) {
                            if (innerAtom.compareAndSet(j, j++)) {
                                if (saturation > list.get(i).getSaturationLimit()) break;
                                if (eatProcess.isEatable(list.get(i), list.get(j))) {
                                    saturation += list.get(j).getWeight();
                                    list.remove(j);
                                }
                            }
                        }
                    }
                });
    }

    private void reproducing(Island island) {
        Utility.getIslandCellStream(island)
                .forEach(list -> {
                    Map<Entities, Long> mapGroupByCount = list.stream().collect(Collectors.groupingBy(Entity::getKind, Collectors.counting()));
                    mapGroupByCount.keySet().forEach(key -> {
                        long countEntitiesOnCell = mapGroupByCount.get(key);
                        int countOfNewBorn = (int) Math.floorDiv(countEntitiesOnCell, 2);
                        if (key != Entities.PLANT && key.getCountOnCell() > (countEntitiesOnCell + countOfNewBorn)) {
                            for (int i = 0; i < countOfNewBorn; i++) {
                                Utility.createEntityAndAdd(list, key);
                            }
                        }
                    });
                });
    }

    private void moving(Island island) {
        Map<Integer, Map<Integer, CopyOnWriteArrayList<Entity>>> islandMap = island.getIslandMap();

        for (Integer xKey : islandMap.keySet()) {
            Map<Integer, CopyOnWriteArrayList<Entity>> innerMap = islandMap.get(xKey);
            for (Integer yKey : innerMap.keySet()) {
                CopyOnWriteArrayList<Entity> cell = innerMap.get(yKey);
                cell.forEach(entity -> {
                    moveProcess.move(island, xKey, yKey, entity);
                });
            }
        }
    }
}
