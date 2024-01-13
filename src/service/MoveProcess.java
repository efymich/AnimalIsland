package service;

import enums.Directions;
import model.Entity;
import model.Island;
import provider.MoveCoordinator;
import utilize.RandomEnumGenerator;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class MoveProcess {
    private volatile static MoveProcess instance;

    private final MoveCoordinator moveCoordinator;

    private MoveProcess(MoveCoordinator moveCoordinator) {
        this.moveCoordinator = moveCoordinator;
    }

    public void move(Island island, int xKey, int yKey, Entity entity) {

        Map<Integer, Map<Integer, CopyOnWriteArrayList<Entity>>> islandMap = island.getIslandMap();
        if (entity.getSpeed() != 0) {
            int[] initCoordinates = new int[]{xKey, yKey};
            int[] newCoordinates = moveCoordinator.getDirectionAndCoordinates(entity, initCoordinates);

            if (moveCoordinator.isEnoughSpace(newCoordinates, island)) {
                CopyOnWriteArrayList<Entity> initList = islandMap.get(initCoordinates[0]).get(initCoordinates[1]);
                CopyOnWriteArrayList<Entity> destinationList = islandMap.get(newCoordinates[0]).get(newCoordinates[1]);
                if (!moveCoordinator.isEnoughAnimals(destinationList, entity)) {
                    destinationList.add(entity);
                    initList.remove(entity);
                }
            }
        }
    }

    public static MoveProcess getInstance() {
        if (instance == null) {
            synchronized (EatProcess.class) {
                if (instance == null) {
                    MoveCoordinator coordinator = new MoveCoordinator();
                    instance = new MoveProcess(coordinator);
                }
                return instance;
            }
        }
        return instance;
    }
}
