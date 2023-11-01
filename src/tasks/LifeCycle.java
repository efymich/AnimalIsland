package tasks;

import entities.Entity;
import entities.Island;
import enums.Directions;
import enums.Entities;
import util.RandomEnumGenerator;
import util.Utility;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Stream;

public class LifeCycle implements Runnable{
    private Island island;

    public LifeCycle(Island island) {
        this.island = island;
    }

    @Override
    public void run() {

    }

    private void eating(){}
    private void moving(Island island){
        RandomEnumGenerator<Directions> generator = new RandomEnumGenerator<>(Directions.class);
        RandomEnumGenerator<Entities> randomEnumGenerator = new RandomEnumGenerator<>(Entities.class);
        Directions direction = (Directions) generator.randomEnum();
        Stream<ArrayList<Entity>> islandCellStream = Utility.getIslandCellStream(island);
        Map<Integer, Map<Integer, ArrayList<Entity>>> islandMap = island.getIslandMap();


    }
    private void multiplying(){}
}
