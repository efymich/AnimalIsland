package tasks;

import enums.Entities;
import models.Entity;
import models.Island;
import services.EatProcess;
import util.Utility;

import java.util.*;
import java.util.stream.Collectors;

public class LifeCycle implements Runnable{
    private final Island island;
    private final EatProcess eatProcess = EatProcess.getInstance();

    public LifeCycle(Island island) {
        this.island = island;
    }

    @Override
    public void run() {
        Thread current = Thread.currentThread();
        while(!current.isInterrupted()){
//            eating(island);
            multiplying(island);
        }
    }

    private void eating(Island island){
        Utility.getIslandCellStream(island).forEach(list -> {
            List<Entity> initialList = new ArrayList<>(list);

            for (int i = 0; i < initialList.size(); i++) {
                double saturationLimit = initialList.get(i).getSaturationLimit();
                double saturation = 0.0;
                for (int j = 0; j < list.size(); j++) {
                    if (i != j){
                        if (saturation > saturationLimit) break;
                        if (eatProcess.isEatable(initialList.get(i),list.get(j))) {
                            saturation += list.get(j).getWeight();
                            list.remove(j);
                            j--;
                        }
                    }
                }
            }
        });
    }
    private void multiplying(Island island){
        Utility.getIslandCellStream(island).forEach(list -> {
            Map<Entities, Long> mapGroupByCount = list.stream().collect(Collectors.groupingBy(Entity::getKind, Collectors.counting()));
            mapGroupByCount.keySet().stream().peek(key -> {
                int countOfNewBorn = (int) Math.floor(mapGroupByCount.get(key));
                if (key != Entities.PLANT) {
                    for (int i = 0; i < countOfNewBorn; i++) {
                        Utility.createEntityAndAdd(list,key);
                    }
                }
            });
        });
    }
    private void moving(Island island){
    }
}
