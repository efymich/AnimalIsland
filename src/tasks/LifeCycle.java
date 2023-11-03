package tasks;

import models.Entity;
import models.Island;
import services.EatProcess;
import util.Utility;

import java.util.ArrayList;
import java.util.stream.Stream;

public class LifeCycle implements Runnable{
    private Island island;
    private final EatProcess eatProcess = EatProcess.getInstance();

    public LifeCycle(Island island) {
        this.island = island;
    }

    @Override
    public void run() {
        System.out.println(eatProcess.getEatingProbs());
    }

    private void eating(Island island){
        Stream<ArrayList<Entity>> islandCellStream = Utility.getIslandCellStream(island);


    }
    private void multiplying(Island island){}
    private void moving(Island island){
    }
}
