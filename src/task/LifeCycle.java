package task;

import enums.Entities;
import model.Entity;
import model.Island;
import provider.EatProvider;
import provider.MoveProvider;
import provider.ReproduceProvider;
import service.EatProcess;
import service.MoveProcess;
import utilize.Utility;

import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class LifeCycle implements Runnable {
    private final Island island;
    private final EatProvider eatProvider = new EatProvider(EatProcess.getInstance());
    private final ReproduceProvider reproduceProvider = new ReproduceProvider();
    private final MoveProvider moveProvider = new MoveProvider(MoveProcess.getInstance());
    public boolean isInterrupted = false;

    public LifeCycle(Island island) {
        this.island = island;
    }

    @Override
    public void run() {
        while (!isInterrupted) {
            eatProvider.eating(island);
            reproduceProvider.reproducing(island);
            moveProvider.moving(island);
        }
    }
}
