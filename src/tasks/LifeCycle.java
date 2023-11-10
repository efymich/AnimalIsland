package tasks;

import enums.Entities;
import models.Entity;
import models.Island;
import services.EatProcess;
import util.Utility;

import java.util.*;
import java.util.concurrent.FutureTask;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;

public class LifeCycle implements Runnable {
    private final Island island;
    private final EatProcess eatProcess = EatProcess.getInstance();
    private final Semaphore eatingSemaphore = new Semaphore(3);
    private final Semaphore multiplyingSemaphore = new Semaphore(0);

    public LifeCycle(Island island) {
        this.island = island;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            eating(island);
//            multiplying(island);
        }
    }

    private void eating(Island island) {
        try {

            eatingSemaphore.acquire();

            Utility.getIslandCellStream(island).forEach(list -> {
                List<Entity> initialList = new ArrayList<>(list);

                for (int i = 0; i < initialList.size(); i++) {
                    double saturationLimit = initialList.get(i).getSaturationLimit();
                    double saturation = 0.0;
                    synchronized (list) {
                        for (int j = 0; j < list.size(); j++) {
                            if (i != j) {
                                if (saturation > saturationLimit) break;
                                if (eatProcess.isEatable(initialList.get(i), list.get(j))) {
                                    saturation += list.get(j).getWeight();
                                    list.remove(j);
                                    j--;

                                }
                            }
                        }
                    }
                }
            });
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            eatingSemaphore.release();
            multiplyingSemaphore.release();
        }
    }

    private void multiplying(Island island) {
        try {
            multiplyingSemaphore.acquire();

            Utility.getIslandCellStream(island).forEach(list -> {
                Map<Entities, Long> mapGroupByCount = list.stream().collect(Collectors.groupingBy(Entity::getKind, Collectors.counting()));
                mapGroupByCount.keySet().stream().peek(key -> {
                    int countOfNewBorn = (int) Math.floor(mapGroupByCount.get(key));
                    if (key != Entities.PLANT) {
                        for (int i = 0; i < countOfNewBorn; i++) {
                            Utility.createEntityAndAdd(list, key);
                        }
                    }
                });
            });
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            multiplyingSemaphore.release();
        }
    }

    private void moving(Island island) {
    }
}
