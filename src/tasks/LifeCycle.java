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

    private final Semaphore eatingSemaphore = new Semaphore(3);
    private final Semaphore reproducingSemaphore = new Semaphore(0);
    private final Semaphore movingSemaphore = new Semaphore(0);

    public LifeCycle(Island island) {
        this.island = island;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                eating(island);
                reproducing(island);
                moving(island);
//                TimeUnit.SECONDS.sleep(3);
            } /*catch (InterruptedException e) {
                throw new RuntimeException(e);
            } */
            finally {

            }
        }
    }

    private void eating(Island island) {
        try {
            eatingSemaphore.acquire();
//            System.out.println(Thread.currentThread().getName());
            Utility.getIslandCellStream(island)
//                    .peek(System.out::println)
                    .forEach(list -> {
                        AtomicInteger outerAtom = new AtomicInteger(0);
                        AtomicInteger innerAtom = new AtomicInteger(1);
                        for (int i = 0; i < list.size(); i++) {
                            if (outerAtom.compareAndSet(i,i++)) {
                                double saturationLimit = list.get(i).getSaturationLimit();
                                double saturation = 0.0;

                                for (int j = i + 1; j < list.size(); j++) {
                                    if (innerAtom.compareAndSet(j,j++)) {
                                        if (saturation > saturationLimit) break;
                                        if (eatProcess.isEatable(list.get(i), list.get(j))) {
                                            saturation += list.get(j).getWeight();
                                            list.remove(j);
                                        }
                                    }
                                }
                            }
                        }
                    });
//            System.out.println("----------EATING-------------------------------");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            eatingSemaphore.release();
//            eatingSemaphore.drainPermits();
            reproducingSemaphore.release();
        }
    }

    private void reproducing(Island island) {
        try {
            reproducingSemaphore.acquire();

//            System.out.println(Thread.currentThread().getName());
            Utility.getIslandCellStream(island)
//                    .peek(System.out::println)
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
//            System.out.println("-------------REPRODUCING----------------------------");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            reproducingSemaphore.release();
//            reproducingSemaphore.drainPermits();
            movingSemaphore.release();
        }
    }

    private void moving(Island island) {
        try {
            movingSemaphore.acquire();

//            System.out.println(Thread.currentThread().getName());
            Map<Integer, Map<Integer, CopyOnWriteArrayList<Entity>>> islandMap = island.getIslandMap();

            for (Integer xKey : islandMap.keySet()) {
                Map<Integer, CopyOnWriteArrayList<Entity>> innerMap = islandMap.get(xKey);
                for (Integer yKey : innerMap.keySet()) {
                    CopyOnWriteArrayList<Entity> cell = innerMap.get(yKey);
//                    System.out.println(cell);
                    cell.forEach(entity -> {
                        moveProcess.move(island, xKey, yKey, entity);
                    });
                }
            }
//            System.out.println("--------------MOVING--------------------------------");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            movingSemaphore.release();
//            movingSemaphore.drainPermits();
//            eatingSemaphore.release();
        }
    }
}
