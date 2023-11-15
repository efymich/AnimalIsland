package app;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.Island;
import tasks.LifeCycle;
import tasks.PlantSeeder;
import tasks.StatisticManager;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Application {

    public void start(){
        try {
            Island island = configureApp();
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
            scheduledExecutorService.scheduleAtFixedRate(new PlantSeeder(island),10,30, TimeUnit.SECONDS);
            scheduledExecutorService.scheduleAtFixedRate(new StatisticManager(island),0,10, TimeUnit.SECONDS);

            int executingSeconds = island.getConfig().getLifeCyclePeriod().getSecond();
            executorService.awaitTermination(executingSeconds,TimeUnit.SECONDS);

            executorService.shutdownNow();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private Island configureApp(){
        Configuration configuration = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.findAndRegisterModules(); // for working with Java 8 LocalData classes
            configuration = mapper.readValue(new File("resources/config/configuration.json"), Configuration.class);
            System.out.println(configuration);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Island.getInstance(configuration);
    }
}
