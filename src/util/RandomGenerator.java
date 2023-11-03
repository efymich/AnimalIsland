package util;

import java.util.SplittableRandom;

public class RandomGenerator {
    private static final SplittableRandom random = new SplittableRandom();
    public boolean beEaten(Double probability){
        return random.nextDouble(0, 1) <= probability;
    }
}
