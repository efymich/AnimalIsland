package provider;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import model.Entity;
import model.Island;
import service.MoveProcess;

import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class MoveProvider {

    MoveProcess moveProcess;

    public MoveProvider(MoveProcess moveProcess) {
        this.moveProcess = moveProcess;
    }

    public void moving(Island island) {
        Map<Integer, Map<Integer, CopyOnWriteArrayList<Entity>>> islandMap = island.getIslandMap();

        for (Integer xKey : islandMap.keySet()) {
            Map<Integer, CopyOnWriteArrayList<Entity>> innerMap = islandMap.get(xKey);
            for (Integer yKey : innerMap.keySet()) {
                CopyOnWriteArrayList<Entity> cell = innerMap.get(yKey);
                cell.forEach(entity -> {
                    moveProcess.move(island, xKey, yKey, entity);
                });
            }
        }
    }
}
