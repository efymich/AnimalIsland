package tasks;

import models.Entity;
import models.Island;
import util.Utility;

import java.util.TreeMap;
import java.util.stream.Collectors;

public class StatisticManager implements Runnable {
    private final Island island;

    public StatisticManager(Island island) {
        this.island = island;
    }

    @Override
    public void run() {
        generateStatistic();
    }

    private void generateStatistic() {
        TreeMap<String, Long> map = Utility.getIslandCellEntityStream(island)
                .collect(Collectors.groupingBy(Entity::toString, TreeMap::new, Collectors.counting()));

        for (String entity : map.keySet()) {
            System.out.println(entity + " : " + map.get(entity));
        }
        System.out.println("----------------------------------");
    }
}
