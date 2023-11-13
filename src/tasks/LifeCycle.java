package tasks;

import enums.Entities;
import models.Entity;
import models.Island;
import services.EatProcess;
import services.MoveProcess;
import util.Utility;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class LifeCycle implements Runnable {
    private final Island island;
    private final EatProcess eatProcess = EatProcess.getInstance();
    private final MoveProcess moveProcess = MoveProcess.getInstance();
    private final Semaphore eatingSemaphore = new Semaphore(3);
    private final Semaphore reproducingSemaphore = new Semaphore(1);
    private final Semaphore movingSemaphore = new Semaphore(1);

    public LifeCycle(Island island) {
        this.island = island;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
//            eating(island);
//            reproducing(island);
            try {
                moving(island);
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void eating(Island island) {
        try {

            eatingSemaphore.acquire();

            System.out.println(Thread.currentThread().getName() + " eating");

            Utility.getIslandCellStream(island).forEach(list -> {
//                List<Entity> initialList = new CopyOnWriteArrayList<>(list);
                for (int i = 0; i < list.size(); i++) {
                    double saturationLimit = list.get(i).getSaturationLimit();
                    double saturation = 0.0;

                    for (int j = list.size()-1; j >= 0; j--) {
                        if (saturation > saturationLimit) break;
                        if (eatProcess.isEatable(list.get(i), list.get(j))) {
                            saturation += list.get(j).getWeight();
                            list.remove(j);
                        }

 /*                       if (i != j) {
                            if (saturation > saturationLimit) break;
                            if (eatProcess.isEatable(list.get(i), list.get(j))) {
                                saturation += list.get(j).getWeight();
                                list.remove(j);
//                                j--;

                            }
                        } */
                    }
                }
            });
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            eatingSemaphore.release();
            reproducingSemaphore.release();
        }
    }

    private void reproducing(Island island) {
        try {
            reproducingSemaphore.acquire();

//            System.out.println(Thread.currentThread().getName() + " REPRODUCING");

            Utility.getIslandCellStream(island).forEach(list -> {
                Map<Entities, Long> mapGroupByCount = list.stream().collect(Collectors.groupingBy(Entity::getKind, Collectors.counting()));
                mapGroupByCount.keySet().forEach(key -> {
                    long countEntitiesOnCell = mapGroupByCount.get(key);
                    int countOfNewBorn = (int) Math.floorDiv(countEntitiesOnCell,2);
                    if (key != Entities.PLANT && key.getCountOnCell() > (countEntitiesOnCell + countOfNewBorn)) {
                        for (int i = 0; i < countOfNewBorn; i++) {
                            Utility.createEntityAndAdd(list, key);
                        }
                    }
                });
            });
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            reproducingSemaphore.release();
            movingSemaphore.release();
        }
    }

    private void moving(Island island) {
        try {
            movingSemaphore.acquire();

            Map<Integer, Map<Integer, CopyOnWriteArrayList<Entity>>> islandMap = island.getIslandMap();

            for (Integer xKey:islandMap.keySet()) {
                Map<Integer, CopyOnWriteArrayList<Entity>> innerMap = islandMap.get(xKey);
                for (Integer yKey : innerMap.keySet()) {
                    CopyOnWriteArrayList<Entity> cell = innerMap.get(yKey);
                    System.out.println(cell);
                    cell.forEach(entity -> {
                        moveProcess.move(island,xKey,yKey,entity);
                    });
                }
            }
            System.out.println("----------------------------------------------");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            movingSemaphore.release();
        }
    }
}
