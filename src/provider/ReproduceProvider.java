package provider;

import enums.Entities;
import model.Entity;
import model.Island;
import utilize.Utility;

import java.util.Map;
import java.util.stream.Collectors;

public class ReproduceProvider {
    public void reproducing(Island island) {
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
}
