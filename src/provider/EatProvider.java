package provider;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import model.Island;
import service.EatProcess;
import utilize.Utility;

import java.util.concurrent.atomic.AtomicInteger;

@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class EatProvider {
    EatProcess eatProcess;

    public EatProvider(EatProcess eatProcess){
        this.eatProcess = eatProcess;
    }

    public void eating(Island island) {
        Utility.getIslandCellStream(island)
                .forEach(list -> {
                    AtomicInteger innerAtom = new AtomicInteger(1);
                    for (int i = 0; i < list.size(); i++) {
                        double saturation = 0.0;

                        for (int j = i + 1; j < list.size(); j++) {
                            if (innerAtom.compareAndSet(j, j++)) {
                                if (saturation > list.get(i).getSaturationLimit()) break;
                                if (eatProcess.isEatable(list.get(i), list.get(j))) {
                                    saturation += list.get(j).getWeight();
                                    list.remove(j);
                                }
                            }
                        }
                    }
                });
    }
}
