package app;

import model.Island;
import service.ConfigManager;
import task.LifeCycle;
import task.PlantSeeder;
import task.StatisticManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Application {

    public void start() {
        try {
            Island island = Island.getInstance(ConfigManager.getConfigManager().getIslandConfiguration());
            ExecutorService executorService = Executors.newCachedThreadPool();

            ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(r -> {
                Thread thread = new Thread(r);
                thread.setDaemon(true);
                return thread;
            });

            LifeCycle lifeCycletask = new LifeCycle(island);

            for (int i = 0; i < 3; i++) {
                executorService.submit(lifeCycletask);
            }
            scheduledExecutorService.scheduleAtFixedRate(new PlantSeeder(island), 10, 30, TimeUnit.SECONDS);
            scheduledExecutorService.scheduleAtFixedRate(new StatisticManager(island), 0, 10, TimeUnit.SECONDS);
            int executingSeconds = island.getConfig().getLifeCyclePeriod().getSecond();

            TimeUnit.SECONDS.sleep(executingSeconds);
            lifeCycletask.isInterrupted = true;

            executorService.shutdownNow();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
