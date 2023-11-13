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
            ScheduledExecutorService seedService = Executors.newScheduledThreadPool(1);
            ScheduledExecutorService statisticService = Executors.newScheduledThreadPool(1);
            ExecutorService executorService = Executors.newFixedThreadPool(3);

            seedService.scheduleAtFixedRate(new PlantSeeder(island),0,30, TimeUnit.SECONDS);
            statisticService.scheduleAtFixedRate(new StatisticManager(island),0,10,TimeUnit.SECONDS);

            LifeCycle lifeCycletask = new LifeCycle(island);
            Thread thread1 = new Thread(lifeCycletask);
            Thread thread2 = new Thread(lifeCycletask);
            Thread thread3 = new Thread(lifeCycletask);
            Thread thread4 = new Thread(lifeCycletask);
            Thread thread5 = new Thread(lifeCycletask);
            Thread thread6 = new Thread(lifeCycletask);

            thread1.start();
            thread2.start();
            thread3.start();
            thread4.start();
            thread5.start();
            thread6.start();

            TimeUnit.MINUTES.sleep(7);
 /*           for (int i = 0; i < 3; i++) {
                executorService.execute(new LifeCycle(island));
            }

            int executingSeconds = island.getConfig().getLifeCyclePeriod().getSecond();
            executorService.awaitTermination(executingSeconds,TimeUnit.SECONDS);

            executorService.shutdownNow();*/
            seedService.shutdownNow();
            statisticService.shutdownNow();
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
